package com.splab.springgames.application.game.port.outbound

import com.splab.springgames.domain.game.domain.Game
import java.util.*

interface GameRepository {

    fun findAll(): List<Game>

    fun getById(id: UUID): Game

}
