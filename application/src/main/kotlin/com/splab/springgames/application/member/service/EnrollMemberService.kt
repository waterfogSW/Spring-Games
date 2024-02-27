package com.splab.springgames.application.member.service

import com.splab.springgames.application.member.port.inbound.EnrollMemberUseCase
import com.splab.springgames.application.member.port.outbound.MemberRepository
import com.splab.springgames.domain.member.domain.Member
import org.springframework.stereotype.Component

@Component
class EnrollMemberService(
    private val memberRepository: MemberRepository,
) : EnrollMemberUseCase {

    override fun invoke(command: EnrollMemberUseCase.Command) {
        Member.create(
            name = command.name,
            email = command.email,
            registeredDate = command.registeredDate,
        ).also { memberRepository.save(it) }
    }

}
