package com.splab.springgames.application.member.service

import com.splab.springgames.application.member.port.inbound.DeleteMemberUseCase
import com.splab.springgames.application.member.port.outbound.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class DeleteMemberService(
    private val memberRepository: MemberRepository,
) : DeleteMemberUseCase {

    @Transactional
    override fun invoke(memberId: UUID) {
        if (memberRepository.existsById(memberId)) {
            memberRepository.deleteById(memberId)
        }
    }

}
