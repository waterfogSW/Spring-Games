package com.splab.springgames.application.member.port.inbound

import java.util.*

interface DeleteGameCardUseCase {

    fun invoke(gameCardId: UUID)

}
