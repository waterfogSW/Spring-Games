package com.splab.springgames.domain.member.domain

import com.splab.springgames.domain.member.vo.GameCardPrice
import com.splab.springgames.domain.member.vo.GameCardSerialNumber
import com.splab.springgames.domain.member.vo.GameCardTitle
import com.splab.springgames.support.common.uuid.UuidGenerator
import java.math.BigDecimal
import java.util.*

data class GameCard(
    val id: UUID = UuidGenerator.create(),
    val memberId: UUID,
    val gameId: UUID,
    val title: GameCardTitle,
    val serialNumber: GameCardSerialNumber,
    val price: GameCardPrice,
) {

    fun isValidCard(): Boolean {
        return price.value != BigDecimal.ZERO
    }

    companion object {

        fun create(
            gameId: UUID,
            userId: UUID,
            title: String,
            serialNumber: Long,
            price: BigDecimal,
        ): GameCard {
            return GameCard(
                gameId = gameId,
                memberId = userId,
                title = GameCardTitle.create(title),
                serialNumber = GameCardSerialNumber.create(serialNumber),
                price = GameCardPrice.create(price),
            )
        }
    }

}
