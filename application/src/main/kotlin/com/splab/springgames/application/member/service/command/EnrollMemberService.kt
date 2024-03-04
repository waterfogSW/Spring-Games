package com.splab.springgames.application.member.service.command

import com.splab.springgames.application.member.port.inbound.EnrollMemberUseCase
import com.splab.springgames.application.member.port.outbound.MemberEventNotifier
import com.splab.springgames.application.member.port.outbound.MemberRepository
import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.exception.MemberExceptionType
import com.splab.springgames.domain.member.vo.Email
import com.splab.springgames.support.common.exception.CustomException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EnrollMemberService(
    private val memberRepository: MemberRepository,
    private val memberEventNotifier: MemberEventNotifier,
) : EnrollMemberUseCase {

    @Transactional
    override fun invoke(command: EnrollMemberUseCase.Command) {
        checkDuplicatedEmail(command.email)

        Member.create(
            name = command.name,
            email = command.email,
            registeredDate = command.registeredDate,
        ).also {
            memberRepository.save(it)
            memberEventNotifier.notifyLevelUpdated(it)
        }
    }

    private fun checkDuplicatedEmail(email: String) {
        if (memberRepository.findByEmail(Email.create(email)) != null) {
            throw CustomException(
                type = MemberExceptionType.DUPLICATED_EMAIL_INPUT,
                message = "이미 등록된 이메일입니다.",
            )
        }
    }

}
