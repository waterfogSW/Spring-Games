package com.splab.springgames.application.game.port.inbound

import com.splab.springgames.domain.game.domain.Game

interface GameQueryUseCase {

    fun findAll(): List<Game>


}
