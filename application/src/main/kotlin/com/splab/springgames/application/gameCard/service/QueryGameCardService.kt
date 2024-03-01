package com.splab.springgames.application.gameCard.service

import com.splab.springgames.application.game.port.inbound.QueryGameUseCase
import com.splab.springgames.application.gameCard.port.inbound.QueryGameCardUseCase
import com.splab.springgames.application.gameCard.port.outbound.GameCardRepository
import com.splab.springgames.application.gameCard.vo.GameCardInfo
import org.springframework.stereotype.Service
import java.util.*

@Service
class QueryGameCardService(
    private val gameCardRepository: GameCardRepository,
    private val queryGameUseCase: QueryGameUseCase,
) : QueryGameCardUseCase {

    override fun findAllInfosByMemberId(memberId: UUID): List<GameCardInfo> {
        return gameCardRepository
            .findAllByMemberId(memberId)
            .map {
                GameCardInfo.of(
                    gameCard = it,
                    game = queryGameUseCase.getById(it.gameId)
                )
            }
    }

}
