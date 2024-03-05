package com.splab.springgames.application.game.port.outbound

import com.splab.springgames.domain.game.domain.Game
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class GameRepositorySpy : GameRepository {

    private val bucket = ConcurrentHashMap<UUID, Game>()

    override fun findAll(): List<Game> {
        return bucket.values.toList()
    }

    override fun getById(id: UUID): Game {
        return bucket[id] ?: throw NoSuchElementException()
    }

    fun save(game: Game) {
        bucket[game.id] = game
    }

}
