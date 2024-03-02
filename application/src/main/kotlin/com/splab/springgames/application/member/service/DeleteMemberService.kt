package com.splab.springgames.application.member.service

import com.splab.springgames.application.member.port.inbound.DeleteMemberUseCase
import com.splab.springgames.application.member.port.outbound.GameCardRepository
import com.splab.springgames.application.member.port.outbound.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class DeleteMemberService(
    private val memberRepository: MemberRepository,
    private val gameCardRepository: GameCardRepository,
) : DeleteMemberUseCase {

    @Transactional
    override fun invoke(memberId: UUID) {
        if (memberRepository.existsById(memberId).not()) {
            return
        }

        memberRepository.deleteById(memberId)
        gameCardRepository.deleteAllByMemberId(memberId)
    }

}
