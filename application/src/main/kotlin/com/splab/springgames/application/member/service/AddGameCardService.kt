package com.splab.springgames.application.member.service

import com.splab.springgames.application.member.port.inbound.AddGameCardUseCase
import com.splab.springgames.application.member.port.outbound.GameCardRepository
import com.splab.springgames.application.member.port.outbound.MemberRepository
import com.splab.springgames.domain.member.domain.GameCard
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AddGameCardService(
    private val gameCardRepository: GameCardRepository,
    private val memberRepository: MemberRepository,
) : AddGameCardUseCase {

    @Transactional
    override fun invoke(command: AddGameCardUseCase.Command) {
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
            .updateLevelWith(memberGameCards)
            .also { memberRepository.save(it) }
    }

}
