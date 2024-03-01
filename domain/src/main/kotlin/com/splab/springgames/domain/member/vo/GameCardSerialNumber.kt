package com.splab.springgames.domain.member.vo

import com.splab.springgames.domain.member.exception.GameCardExceptionType
import com.splab.springgames.support.common.exception.CustomException

@JvmInline
value class GameCardSerialNumber(val value: Long) {

    init {
        require(validation(value)) { VALIDATION_MESSAGE }
    }

    companion object {

        fun create(value: Long): GameCardSerialNumber {
            if (validation(value).not()) {
                throw CustomException(
                    type = GameCardExceptionType.INVALID_SERIAL_NUMBER_INPUT,
                    message = VALIDATION_MESSAGE,
                )
            }

            return GameCardSerialNumber(value)
        }

        private const val VALIDATION_MESSAGE = "게임 카드 시리얼 번호는 1 이상이어야 합니다."

        private fun validation(value: Long): Boolean {
            return value > 0
        }
    }

}
