package com.splab.springgames.application.gameCard.port.inbound

import com.splab.springgames.application.gameCard.vo.GameCardInfo
import java.util.*

interface QueryGameCardUseCase {

    fun findAllInfosByMemberId(memberId: UUID): List<GameCardInfo>

}
