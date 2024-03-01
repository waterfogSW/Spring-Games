package com.splab.springgames.application.gameCard.port.outbound

import com.splab.springgames.domain.gameCard.domain.GameCard
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class GameCardRepositorySpy : GameCardRepository {

    private val bucket = ConcurrentHashMap<UUID, GameCard>()

    override fun save(gameCard: GameCard) {
        bucket[gameCard.id] = gameCard
    }

    fun findByGameIdAndSerialNumber(
        gameId: UUID,
        serialNumber: Long
    ): GameCard? {
        return bucket.values.find {
            it.gameId == gameId && it.serialNumber.value == serialNumber
        }
    }

}
