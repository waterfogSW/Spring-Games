package com.splab.springgames.domain.member.vo

import com.splab.springgames.domain.member.exception.MemberExceptionType
import com.splab.springgames.support.common.exception.CustomException

@JvmInline
value class Name(val value: String) {

    init {
        require(value.length in 2..100) { VALIDATION_MESSAGE }
    }

    companion object {

        const val VALIDATION_MESSAGE = "회원 이름은 2자 이상 100자 이하로 입력해주세요(한, 영, 숫자, 특수문자, 공백포함)"

        fun create(value: String): Name {
            if ((value.length in 2..100).not()) {
                throw CustomException(
                    type = MemberExceptionType.ENROLLMENT_INVALID_NAME_INPUT,
                    message = VALIDATION_MESSAGE
                )
            }
            return Name(value)
        }

    }

}
