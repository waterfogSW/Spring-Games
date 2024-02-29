package com.splab.springgames.application.game.port.inbound

import com.splab.springgames.domain.game.entity.Game

interface GameQueryUseCase {

    fun findAll(): List<Game>


}
