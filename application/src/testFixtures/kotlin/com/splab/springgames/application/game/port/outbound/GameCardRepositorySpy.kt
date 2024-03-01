package com.splab.springgames.application.game.port.outbound

import com.splab.springgames.application.member.port.outbound.GameCardRepository
import com.splab.springgames.domain.member.domain.GameCard
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class GameCardRepositorySpy : GameCardRepository {

    private val bucket = ConcurrentHashMap<UUID, GameCard>()

    override fun save(gameCard: GameCard) {
        bucket[gameCard.id] = gameCard
    }

    override fun findAllByMemberId(memberId: UUID): List<GameCard> {
        return bucket.values.filter { it.memberId == memberId }
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
