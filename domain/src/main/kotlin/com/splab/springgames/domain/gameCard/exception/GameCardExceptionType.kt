package com.splab.springgames.domain.gameCard.exception

import com.splab.springgames.support.common.exception.CustomExceptionType

enum class GameCardExceptionType(override val code: String) : CustomExceptionType {
    INVALID_TITLE_INPUT("GAME-CARD-001"),
    INVALID_SERIAL_NUMBER_INPUT("GAME-CARD-002"),
    INVALID_PRICE_INPUT("GAME-CARD-003"),
    INVALID_GAME_ID_INPUT("GAME-CARD-004"),
}
