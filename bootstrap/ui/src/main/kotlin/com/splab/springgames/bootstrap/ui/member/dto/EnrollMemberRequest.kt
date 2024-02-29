package com.splab.springgames.bootstrap.ui.member.dto

import com.splab.springgames.application.member.port.inbound.EnrollMemberUseCase
import com.splab.springgames.domain.member.exception.MemberExceptionType
import com.splab.springgames.support.common.exception.CustomException
import java.time.LocalDate

data class EnrollMemberRequest(
    val name: String?,
    val email: String?,
    val registeredDate: LocalDate?,
) {

    fun toCommand(): EnrollMemberUseCase.Command {
        return EnrollMemberUseCase.Command(
            name = getNameOrThrow(name),
            email = getEmailOrThrow(email),
            registeredDate = getRegisteredDateOrThrow(registeredDate)
        )
    }

    private fun getNameOrThrow(name: String?): String {
        if (name.isNullOrBlank()) {
            throw CustomException(
                type = MemberExceptionType.INVALID_NAME_INPUT,
                message = "이름은 필수 입력값입니다."
            )
        }
        return name
    }

    private fun getEmailOrThrow(email: String?): String {
        if (email.isNullOrBlank()) {
            throw CustomException(
                type = MemberExceptionType.INVALID_EMAIL_INPUT,
                message = "이메일은 필수 입력값입니다."
            )
        }
        return email
    }

    private fun getRegisteredDateOrThrow(registeredDate: LocalDate?): LocalDate {
        if (registeredDate == null) {
            throw CustomException(
                type = MemberExceptionType.INVALID_REGISTER_DATE_INPUT,
                message = "가입일은 필수 입력값입니다."
            )
        }
        return registeredDate
    }

}
