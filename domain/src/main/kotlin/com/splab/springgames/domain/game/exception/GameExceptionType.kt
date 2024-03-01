package com.splab.springgames.domain.game.exception

import com.splab.springgames.support.common.exception.CustomExceptionType

enum class GameExceptionType (override val code: String): CustomExceptionType {
    GAME_NOT_FOUND("GAME-001"),
}
