package com.splab.springgames.application.member.port.outbound

import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.enum.Level

interface MemberRepository {

    fun save(member: Member)

    fun searchByFilter(
        name: String? = null,
        level: Level? = null,
    ): List<Member>


}
