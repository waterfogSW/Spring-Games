package com.splab.springgames.domain.member.vo

import com.splab.springgames.domain.member.exception.GameCardExceptionType
import com.splab.springgames.support.common.exception.CustomException

@JvmInline
value class GameCardTitle(val value: String) {

    init {
        require(isValidLength(value)) { VALIDATION_MESSAGE }
    }

    companion object {

        fun create(value: String): GameCardTitle {
            if (isValidLength(value).not()) {
                throw CustomException(
                    type = GameCardExceptionType.INVALID_TITLE_INPUT,
                    message = VALIDATION_MESSAGE,
                )
            }

            return GameCardTitle(value)
        }

        private const val VALIDATION_MESSAGE = "게임 카드 제목은 1자 이상 100자 이하여야 합니다."

        private fun isValidLength(value: String): Boolean {
            return value.length in 1..100
        }

    }

}
