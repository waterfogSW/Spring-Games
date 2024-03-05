package com.splab.springgames.application.member.port.inbound

import java.util.*

interface DeleteMemberUseCase {

    fun invoke(memberId: UUID)

}
