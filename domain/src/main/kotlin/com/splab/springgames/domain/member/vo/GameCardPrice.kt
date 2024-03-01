package com.splab.springgames.domain.member.vo

import com.splab.springgames.domain.member.exception.GameCardExceptionType
import com.splab.springgames.support.common.exception.CustomException
import java.math.BigDecimal
import java.math.RoundingMode

@JvmInline
value class GameCardPrice(val value: BigDecimal) {

    init {
        require(validation(value)) { VALIDATION_MESSAGE }
    }

    companion object {

        fun create(value: BigDecimal): GameCardPrice {
            if (validation(value).not()) {
                throw CustomException(
                    type = GameCardExceptionType.INVALID_PRICE_INPUT,
                    message = VALIDATION_MESSAGE,
                )
            }

            return GameCardPrice(roundHalfUpToTwoDecimal(value))
        }

        private const val VALIDATION_MESSAGE = "게임 카드 가격은 0 이상, 100,000 이하의 숫자여야 합니다."

        private fun validation(value: BigDecimal): Boolean {
            return value >= BigDecimal.ZERO && value <= BigDecimal(100_000)
        }

        private fun roundHalfUpToTwoDecimal(value: BigDecimal): BigDecimal {
            return if (value.scale() > 2) {
                value.setScale(2, RoundingMode.HALF_UP)
            } else {
                value
            }
        }
    }

}
