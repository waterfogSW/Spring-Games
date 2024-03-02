package com.splab.springgames.application.member.service

import com.splab.springgames.application.member.port.inbound.DeleteGameCardUseCase
import com.splab.springgames.application.member.port.outbound.GameCardRepository
import com.splab.springgames.application.member.port.outbound.MemberRepository
import com.splab.springgames.domain.member.domain.GameCard
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class DeleteGameCardService(
    private val gameCardRepository: GameCardRepository,
    private val memberRepository: MemberRepository,
) : DeleteGameCardUseCase {

    @Transactional
    override fun invoke(gameCardId: UUID) {
        val gameCard: GameCard = gameCardRepository
            .findById(gameCardId)
            ?.also { gameCardRepository.deleteById(it.id) }
            ?: return

        val memberGameCards: List<GameCard> =
            gameCardRepository.findAllByMemberId(gameCard.memberId)

        memberRepository
            .getById(gameCard.memberId)
            .deleteGameCard(gameCard)
            .updateLevelWith(memberGameCards)
            .also { memberRepository.save(it) }
    }

}
