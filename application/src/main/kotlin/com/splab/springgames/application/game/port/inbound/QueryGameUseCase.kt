package com.splab.springgames.application.game.port.inbound

import com.splab.springgames.domain.game.domain.Game

interface QueryGameUseCase {

    fun findAll(): List<Game>

}
