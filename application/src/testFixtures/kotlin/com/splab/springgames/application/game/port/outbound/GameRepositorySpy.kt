package com.splab.springgames.application.game.port.outbound

import com.splab.springgames.domain.game.entity.Game
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

class GameRepositorySpy: GameRepository {

    private val bucket = ConcurrentHashMap<UUID, Game>()

    override fun findAll(): List<Game> {
        return bucket.values.toList()
    }

    fun save(game: Game) {
        bucket[game.id] = game
    }

}
