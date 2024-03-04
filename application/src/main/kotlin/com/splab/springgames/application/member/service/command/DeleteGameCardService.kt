package com.splab.springgames.application.member.service.command

import com.splab.springgames.application.member.port.inbound.DeleteGameCardUseCase
import com.splab.springgames.application.member.port.outbound.GameCardRepository
import com.splab.springgames.application.member.port.outbound.MemberEventNotifier
import com.splab.springgames.application.member.port.outbound.MemberRepository
import com.splab.springgames.domain.member.domain.GameCard
import com.splab.springgames.domain.member.domain.Member
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class DeleteGameCardService(
    private val gameCardRepository: GameCardRepository,
    private val memberRepository: MemberRepository,
    private val memberEventNotifier: MemberEventNotifier,
) : DeleteGameCardUseCase {

    @Transactional
    override fun invoke(gameCardId: UUID) {
        val gameCard: GameCard = gameCardRepository
            .findById(gameCardId)
            ?.also { gameCardRepository.deleteById(it.id) }
            ?: return

        val memberGameCards: List<GameCard> =
            gameCardRepository.findAllByMemberId(gameCard.memberId)

        val existsMember: Member = memberRepository.getById(gameCard.memberId)

        existsMember
            .deleteGameCard(gameCard)
            .updateLevelWith(memberGameCards)
            .also {
                memberRepository.save(it)
                notifyLevelUpdated(
                    existsMember = existsMember,
                    updatedMember = it,
                )
            }
    }

    private fun notifyLevelUpdated(
        existsMember: Member,
        updatedMember: Member,
    ) {
        if (existsMember.level != updatedMember.level) {
            memberEventNotifier.notifyLevelUpdated(updatedMember)
        }
    }

}
