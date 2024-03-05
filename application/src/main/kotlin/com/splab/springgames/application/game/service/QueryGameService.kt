package com.splab.springgames.application.game.service

import com.splab.springgames.application.game.port.inbound.QueryGameUseCase
import com.splab.springgames.application.game.port.outbound.GameRepository
import com.splab.springgames.domain.game.domain.Game
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class QueryGameService(
    private val gameRepository: GameRepository,
) : QueryGameUseCase {

    @Transactional(readOnly = true)
    override fun findAll(): List<Game> {
        return gameRepository.findAll()
    }

    @Transactional(readOnly = true)
    override fun getById(id: UUID): Game {
        return gameRepository.getById(id)
    }

}
