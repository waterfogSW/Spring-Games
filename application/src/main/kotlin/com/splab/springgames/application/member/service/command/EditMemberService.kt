package com.splab.springgames.application.member.service.command

import com.splab.springgames.application.member.port.inbound.EditMemberUseCase
import com.splab.springgames.application.member.port.outbound.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EditMemberService(
    private val memberRepository: MemberRepository,
) : EditMemberUseCase {

    @Transactional
    override fun invoke(command: EditMemberUseCase.Command) {
        memberRepository
            .getById(command.memberId)
            .update(
                name = command.name,
                email = command.email,
                registeredDate = command.registeredDate
            )
            .also { memberRepository.save(it) }
    }

}
