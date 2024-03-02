package com.splab.springgames.application.member.port.inbound

import java.util.UUID

interface DeleteGameCardUseCase {

    fun invoke(gameCardId: UUID)

}
