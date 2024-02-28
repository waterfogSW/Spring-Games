package com.splab.springgames.domain.member.vo

import java.time.LocalDateTime

@JvmInline
value class RegisteredDate(val value: LocalDateTime) {

    companion object {

        fun create(value: LocalDateTime): RegisteredDate {
            val current = LocalDateTime.now()
            val oneYearAgo = current.minusYears(1)

            require(current.isEqual(value) || (value.isAfter(oneYearAgo) && value.isBefore(current))) {
                "가입일은 현재 시간 또는 1년 이내로 입력해주세요."
            }
            return RegisteredDate(value)
        }

    }

}
