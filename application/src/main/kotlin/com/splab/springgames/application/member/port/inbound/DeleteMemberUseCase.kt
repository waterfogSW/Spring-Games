package com.splab.springgames.application.member.port.inbound

import java.util.UUID

interface DeleteMemberUseCase {

    fun invoke(memberId: UUID)

}
