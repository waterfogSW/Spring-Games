package com.splab.springgames.domain.member.vo

@JvmInline
value class Name(val value: String) {

    init {
        require(value.length in 2..100) {
            "회원 이름은 2자 이상 100자 이하로 입력해주세요(한, 영, 숫자, 특수문자, 공백포함)"
        }
    }
}
