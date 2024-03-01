package com.splab.springgames.domain.member

import com.splab.springgames.domain.member.domain.GameCard
import com.splab.springgames.domain.member.vo.GameCardPrice
import com.splab.springgames.domain.member.vo.GameCardSerialNumber
import com.splab.springgames.domain.member.vo.GameCardTitle
import com.splab.springgames.support.common.uuid.UuidGenerator
import java.math.BigDecimal
import java.util.*

object GameCardFixtureFactory {

    fun create(
        gameId: UUID = UuidGenerator.create(),
        memberId: UUID = UuidGenerator.create(),
        title: String = "게임 제목",
        serialNumber: Long = 1234567890L,
        price: BigDecimal = BigDecimal(10000),
    ): GameCard {
        return GameCard(
            gameId = gameId,
            memberId = memberId,
            title = GameCardTitle(title),
            serialNumber = GameCardSerialNumber(serialNumber),
            price = GameCardPrice(price),
        )
    }
}
