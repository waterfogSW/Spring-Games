package com.splab.springgames.application.member.port.inbound

import com.splab.springgames.application.member.vo.GameCardInfo
import java.util.*

interface QueryGameCardUseCase {

    fun findAllInfosByMemberId(memberId: UUID): List<GameCardInfo>

}
