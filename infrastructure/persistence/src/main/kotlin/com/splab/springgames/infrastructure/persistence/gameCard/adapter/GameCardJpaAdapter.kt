package com.splab.springgames.infrastructure.persistence.gameCard.adapter

import com.splab.springgames.application.gameCard.port.outbound.GameCardRepository
import com.splab.springgames.domain.gameCard.domain.GameCard
import com.splab.springgames.infrastructure.persistence.gameCard.entity.GameCardJpaEntity.Companion.toJpaEntity
import com.splab.springgames.infrastructure.persistence.gameCard.repository.GameCardJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class GameCardJpaAdapter(
    private val gameCardJpaRepository: GameCardJpaRepository,
) : GameCardRepository {

    override fun save(gameCard: GameCard) {
        gameCard
            .toJpaEntity()
            .also { gameCardJpaRepository.save(it) }
    }

    override fun findAllByMemberId(memberId: UUID): List<GameCard> {
        return gameCardJpaRepository
            .findAllByMemberId(memberId)
            .map { it.toDomain() }
    }

}
