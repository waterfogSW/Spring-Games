package com.splab.springgames.application.member.port.outbound

import com.splab.springgames.domain.member.domain.GameCard
import java.util.*

interface GameCardRepository {

    fun save(gameCard: GameCard)

    fun findAllByMemberId(memberId: UUID): List<GameCard>

}
