package com.splab.springgames.application.member.port.inbound

import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.enum.Level
import com.splab.springgames.domain.member.vo.Name

interface QueryMemberUseCase {

    fun searchByFilter(
        name: Name?,
        level: Level?,
    ): List<Member>

}
