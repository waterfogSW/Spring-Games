package com.splab.springgames.infrastructure.persistence.game.adapter

import com.splab.springgames.application.game.port.outbound.GameRepository
import com.splab.springgames.domain.game.domain.Game
import com.splab.springgames.domain.game.exception.GameExceptionType
import com.splab.springgames.infrastructure.persistence.game.repository.GameJpaRepository
import com.splab.springgames.support.common.exception.CustomException
import org.springframework.stereotype.Component
import java.util.*

@Component
class GameJpaAdapter(
    private val gameJpaRepository: GameJpaRepository,
) : GameRepository {

    override fun findAll(): List<Game> {
        return gameJpaRepository
            .findAll()
            .map { it.toDomain() }
    }

    override fun getById(id: UUID): Game {
        return gameJpaRepository
            .findById(id)
            .orElseThrow {
                throw CustomException(
                    type = GameExceptionType.GAME_NOT_FOUND,
                    message = "게임을 찾을 수 없습니다. id: $id"
                )
            }
            .toDomain()
    }


}
