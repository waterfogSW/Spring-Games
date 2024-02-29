package com.splab.springgames.application.game.port.outbound

import com.splab.springgames.domain.game.domain.Game

interface GameRepository {

    fun findAll(): List<Game>

}
