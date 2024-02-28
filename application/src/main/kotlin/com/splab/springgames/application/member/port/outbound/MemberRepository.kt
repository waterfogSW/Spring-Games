package com.splab.springgames.application.member.port.outbound

import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.enum.Level
import com.splab.springgames.domain.member.vo.Name

interface MemberRepository {

    fun save(member: Member)

    fun searchByFilter(
        name: Name? = null,
        level: Level? = null,
    ): List<Member>


}
