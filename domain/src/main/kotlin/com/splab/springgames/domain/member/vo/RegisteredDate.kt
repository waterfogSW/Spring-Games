package com.splab.springgames.domain.member.vo

import com.splab.springgames.domain.member.exception.MemberExceptionType
import com.splab.springgames.support.common.exception.CustomException
import java.time.LocalDate

@JvmInline
value class RegisteredDate(val value: LocalDate) {

    companion object {

        private const val VALIDATION_MESSAGE = "가입일은 현재 시간 부터 1년 이내로 입력해주세요."

        fun create(value: LocalDate): RegisteredDate {
            if (isWithinOneYearFromNow(value).not()) {
                throw CustomException(
                    type = MemberExceptionType.INVALID_REGISTER_DATE_INPUT,
                    message = VALIDATION_MESSAGE
                )
            }
            return RegisteredDate(value)
        }

        private fun isWithinOneYearFromNow(value: LocalDate): Boolean {
            val current = LocalDate.now()
            val oneYearAgo = current.minusYears(1)
            return current.isEqual(value) || (value.isAfter(oneYearAgo) && value.isBefore(current))
        }

    }

}
