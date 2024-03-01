package com.splab.springgames.application.gameCard.vo

import com.splab.springgames.domain.game.domain.Game
import com.splab.springgames.domain.game.vo.GameName
import com.splab.springgames.domain.gameCard.domain.GameCard
import java.math.BigDecimal
import java.util.UUID

data class GameCardInfo(
    val id: UUID,
    val gameName: GameName,
    val title: String,
    val serialNumber: Long,
    val price: BigDecimal,
) {

    companion object {

        fun of(
            gameCard: GameCard,
            game: Game,
        ): GameCardInfo {
            return GameCardInfo(
                id = gameCard.id,
                gameName = game.name,
                title = gameCard.title.value,
                serialNumber = gameCard.serialNumber.value,
                price = gameCard.price.value,
            )
        }

    }

}
