package com.splab.springgames.application.member.port.outbound

import com.splab.springgames.domain.member.domain.GameCard
import com.splab.springgames.domain.member.vo.GameCardSerialNumber
import java.util.*

interface GameCardRepository {

    fun save(gameCard: GameCard)

    fun deleteById(gameCardId: UUID)

    fun deleteAllByMemberId(memberId: UUID)

    fun findAllByMemberId(memberId: UUID): List<GameCard>

    fun existsByGameIdAndSerialNumber(
        gameId: UUID,
        serialNumber: GameCardSerialNumber
    ): Boolean

    fun findById(gameCardId: UUID): GameCard?

}
