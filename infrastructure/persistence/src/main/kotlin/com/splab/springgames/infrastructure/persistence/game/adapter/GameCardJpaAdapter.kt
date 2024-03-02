package com.splab.springgames.infrastructure.persistence.game.adapter

import com.splab.springgames.application.member.port.outbound.GameCardRepository
import com.splab.springgames.domain.member.domain.GameCard
import com.splab.springgames.domain.member.vo.GameCardSerialNumber
import com.splab.springgames.infrastructure.persistence.game.entity.GameCardJpaEntity.Companion.toJpaEntity
import com.splab.springgames.infrastructure.persistence.game.repository.GameCardJpaRepository
import org.springframework.stereotype.Component
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Component
class GameCardJpaAdapter(
    private val gameCardJpaRepository: GameCardJpaRepository,
) : GameCardRepository {

    override fun save(gameCard: GameCard) {
        gameCard
            .toJpaEntity()
            .also { gameCardJpaRepository.save(it) }
    }

    override fun deleteById(gameCardId: UUID) {
        gameCardJpaRepository.deleteById(gameCardId)
    }

    override fun findAllByMemberId(memberId: UUID): List<GameCard> {
        return gameCardJpaRepository
            .findAllByMemberId(memberId)
            .map { it.toDomain() }
    }

    override fun existsByGameIdAndSerialNumber(
        gameId: UUID,
        serialNumber: GameCardSerialNumber
    ): Boolean {
        return gameCardJpaRepository.existsByGameIdAndSerialNumber(
            gameId = gameId,
            serialNumber = serialNumber.value
        )
    }

    override fun findById(gameCardId: UUID): GameCard? {
        return gameCardJpaRepository
            .findById(gameCardId)
            .map { it.toDomain() }
            .getOrNull()
    }

}
