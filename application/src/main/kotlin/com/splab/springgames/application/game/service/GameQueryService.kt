package com.splab.springgames.application.game.service

import com.splab.springgames.application.game.port.inbound.GameQueryUseCase
import com.splab.springgames.application.game.port.outbound.GameRepository
import com.splab.springgames.domain.game.entity.Game
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GameQueryService(
    private val gameRepository: GameRepository,
): GameQueryUseCase {

    @Transactional(readOnly = true)
    override fun findAll(): List<Game> {
        return gameRepository.findAll()
    }

}
