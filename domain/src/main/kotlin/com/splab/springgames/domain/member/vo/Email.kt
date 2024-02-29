package com.splab.springgames.domain.member.vo

import com.splab.springgames.domain.member.exception.MemberExceptionType
import com.splab.springgames.support.common.exception.CustomException

@JvmInline
value class Email(val value: String) {

    init {
        require(value.matches(VALIDATION_REGEX.toRegex())) { VALIDATION_MESSAGE }
    }

    companion object {

        private const val VALIDATION_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"
        private const val VALIDATION_MESSAGE = "잘못된 Email 형식 입니다."

        fun create(value: String): Email {
            if (!value.matches(VALIDATION_REGEX.toRegex())) {
                throw CustomException(
                    type = MemberExceptionType.INVALID_EMAIL_INPUT,
                    message = VALIDATION_MESSAGE
                )
            }
            return Email(value)
        }

    }

}
