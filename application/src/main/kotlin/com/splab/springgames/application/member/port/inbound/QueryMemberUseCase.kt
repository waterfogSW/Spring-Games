package com.splab.springgames.application.member.port.inbound

import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.enum.Level

interface QueryMemberUseCase {

    fun searchByFilter(
        name: String?,
        level: Level?,
    ): List<Member>

}
