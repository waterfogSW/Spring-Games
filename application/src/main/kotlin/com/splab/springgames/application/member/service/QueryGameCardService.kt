package com.splab.springgames.application.member.service

import com.splab.springgames.application.game.port.inbound.QueryGameUseCase
import com.splab.springgames.application.member.port.inbound.QueryGameCardUseCase
import com.splab.springgames.application.member.port.outbound.GameCardRepository
import com.splab.springgames.application.member.vo.GameCardInfo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class QueryGameCardService(
    private val gameCardRepository: GameCardRepository,
    private val queryGameUseCase: QueryGameUseCase,
) : QueryGameCardUseCase {

    @Transactional(readOnly = true)
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
