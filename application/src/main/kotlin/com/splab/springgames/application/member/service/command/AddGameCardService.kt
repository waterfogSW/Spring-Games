package com.splab.springgames.application.member.service.command

import com.splab.springgames.application.member.port.inbound.AddGameCardUseCase
import com.splab.springgames.application.member.port.outbound.GameCardRepository
import com.splab.springgames.application.member.port.outbound.MemberEventNotifier
import com.splab.springgames.application.member.port.outbound.MemberRepository
import com.splab.springgames.domain.member.domain.GameCard
import com.splab.springgames.domain.member.exception.GameCardExceptionType
import com.splab.springgames.domain.member.vo.GameCardSerialNumber
import com.splab.springgames.support.common.exception.CustomException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AddGameCardService(
    private val gameCardRepository: GameCardRepository,
    private val memberRepository: MemberRepository,
    private val memberEventNotifier: MemberEventNotifier,
) : AddGameCardUseCase {

    @Transactional
    override fun invoke(command: AddGameCardUseCase.Command) {
        checkDuplicateSerialNumber(command.gameId, command.serialNumber)
        val gameCard: GameCard = GameCard.create(
            gameId = command.gameId,
            userId = command.memberId,
            title = command.title,
            serialNumber = command.serialNumber,
            price = command.price,
        ).also { gameCardRepository.save(it) }

        val memberGameCards: List<GameCard> = gameCardRepository.findAllByMemberId(command.memberId)

        memberRepository
            .getById(command.memberId)
            .addGameCard(gameCard)
            .updateLevelWith(memberGameCards) { memberEventNotifier.notifyLevelUpdated(it) }
            .also { memberRepository.save(it) }
    }

    private fun checkDuplicateSerialNumber(
        gameId: UUID,
        serialNumber: Long,
    ) {
        val isDuplicated: Boolean = gameCardRepository.existsByGameIdAndSerialNumber(
            gameId = gameId,
            serialNumber = GameCardSerialNumber.create(serialNumber)
        )

        if (isDuplicated) {
            throw CustomException(
                type = GameCardExceptionType.INVALID_SERIAL_NUMBER_INPUT,
                message = "해당 게임에 이미 등록된 카드 시리얼 번호입니다",
            )
        }
    }

}
