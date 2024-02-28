package com.splab.springgames.application.member.service

import com.splab.springgames.application.member.port.inbound.EnrollMemberUseCase
import com.splab.springgames.application.member.port.outbound.MemberRepository
import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.vo.Email
import com.splab.springgames.domain.member.vo.Name
import com.splab.springgames.domain.member.vo.RegisteredDate
import org.springframework.stereotype.Component

@Component
class EnrollMemberService(
    private val memberRepository: MemberRepository,
) : EnrollMemberUseCase {

    override fun invoke(command: EnrollMemberUseCase.Command) {
        Member.create(
            name = Name(command.name),
            email = Email(command.email),
            registeredDate = RegisteredDate.create(command.registeredDate),
        ).also { memberRepository.save(it) }
    }

}
