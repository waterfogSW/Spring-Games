package com.splab.springgames.application.game.port.inbound

import com.splab.springgames.domain.game.domain.Game
import java.util.*

interface QueryGameUseCase {

    fun findAll(): List<Game>

    fun getById(id: UUID): Game

}
