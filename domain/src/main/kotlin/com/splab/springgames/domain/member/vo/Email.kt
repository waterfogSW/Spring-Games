package com.splab.springgames.domain.member.vo

@JvmInline
value class Email(val value: String) {

    init {
        require(value.matches(VALIDATION_REGEX.toRegex())) { VALIDATION_MESSAGE }
    }

    companion object {

        const val VALIDATION_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"
        const val VALIDATION_MESSAGE = "잘못된 Email 형식 입니다."

    }

}
