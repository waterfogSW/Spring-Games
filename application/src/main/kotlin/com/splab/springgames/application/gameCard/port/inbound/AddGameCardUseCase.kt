package com.splab.springgames.application.gameCard.port.inbound

import java.math.BigDecimal
import java.util.*

interface AddGameCardUseCase {

    fun invoke(command: Command)

    data class Command(
        val gameId: UUID,
        val memberId: UUID,
        val title: String,
        val serialNumber: Long,
        val price: BigDecimal,
    )

}
