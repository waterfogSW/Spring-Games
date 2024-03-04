package com.splab.springgames.application.member.service.command

import com.splab.springgames.application.member.port.inbound.EditMemberUseCase
import com.splab.springgames.application.member.port.outbound.MemberRepository
import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.exception.MemberExceptionType
import com.splab.springgames.domain.member.vo.Email
import com.splab.springgames.support.common.exception.CustomException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class EditMemberService(
    private val memberRepository: MemberRepository,
) : EditMemberUseCase {

    @Transactional
    override fun invoke(command: EditMemberUseCase.Command) {
        val member: Member = memberRepository.getById(command.memberId)

        validateEmailUpdatable(member.id, command.email)

        member.update(
            email = command.email,
            name = command.name,
            registeredDate = command.registeredDate,
        ).also { memberRepository.save(it) }
    }

    private fun validateEmailUpdatable(
        targetMemberId: UUID,
        emailString: String
    ) {
        val email: Email = Email.create(emailString)
        val emailMember: Member? = memberRepository.findByEmail(email)

        if (emailMember != null && emailMember.id != targetMemberId) {
            throw CustomException(
                type = MemberExceptionType.DUPLICATED_EMAIL_INPUT,
                message = "이미 사용중인 이메일입니다.",
            )
        }
    }


}
