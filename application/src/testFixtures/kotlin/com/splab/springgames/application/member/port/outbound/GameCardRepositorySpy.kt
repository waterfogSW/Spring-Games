package com.splab.springgames.application.member.port.outbound

import com.splab.springgames.domain.member.domain.GameCard
import com.splab.springgames.domain.member.vo.GameCardSerialNumber
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class GameCardRepositorySpy : GameCardRepository {

    private val bucket = ConcurrentHashMap<UUID, GameCard>()

    override fun save(gameCard: GameCard) {
        bucket[gameCard.id] = gameCard
    }

    override fun deleteById(gameCardId: UUID) {
        bucket.remove(gameCardId)
    }

    override fun deleteAllByMemberId(memberId: UUID) {
        bucket.values.removeIf { it.memberId == memberId }
    }

    override fun findAllByMemberId(memberId: UUID): List<GameCard> {
        return bucket.values.filter { it.memberId == memberId }
    }

    override fun existsByGameIdAndSerialNumber(
        gameId: UUID,
        serialNumber: GameCardSerialNumber
    ): Boolean {
        return bucket.values.any {
            it.gameId == gameId && it.serialNumber == serialNumber
        }
    }

    override fun findById(gameCardId: UUID): GameCard? {
        return bucket[gameCardId]
    }

    fun clear() {
        bucket.clear()
    }

}
