package com.splab.springgames.infrastructure.persistence.game.adapter

import com.splab.springgames.application.game.port.outbound.GameRepository
import com.splab.springgames.domain.game.entity.Game
import com.splab.springgames.infrastructure.persistence.game.repository.GameJpaRepository
import org.springframework.stereotype.Component

@Component
class GameJpaAdapter(
    private val gameJpaRepository: GameJpaRepository,
) : GameRepository{

    override fun findAll(): List<Game> {
        return gameJpaRepository
            .findAll()
            .map { it.toDomain() }
    }


}
