package com.splab.springgames.application.gameCard.port.outbound

import com.splab.springgames.domain.gameCard.domain.GameCard

interface GameCardRepository {

    fun save(gameCard: GameCard)

}
