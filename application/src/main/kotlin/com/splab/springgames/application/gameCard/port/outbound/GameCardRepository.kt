package com.splab.springgames.application.gameCard.port.outbound

import com.splab.springgames.domain.gameCard.domain.GameCard
import java.util.*

interface GameCardRepository {

    fun save(gameCard: GameCard)

    fun findAllByMemberId(memberId: UUID): List<GameCard>

}
