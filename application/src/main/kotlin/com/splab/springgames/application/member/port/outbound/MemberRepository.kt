package com.splab.springgames.application.member.port.outbound

import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.enum.Level
import java.util.UUID

interface MemberRepository {

    fun save(member: Member)

    fun searchByFilter(
        name: String? = null,
        level: Level? = null,
    ): List<Member>

    fun getById(id: UUID): Member?

}
