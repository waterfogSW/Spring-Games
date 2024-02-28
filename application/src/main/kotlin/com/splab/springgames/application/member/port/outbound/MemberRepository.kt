package com.splab.springgames.application.member.port.outbound

import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.enum.Level
import com.splab.springgames.domain.member.vo.Email
import java.util.UUID

interface MemberRepository {

    fun save(member: Member)

    fun searchByFilter(
        name: String? = null,
        level: Level? = null,
    ): List<Member>

    fun getById(id: UUID): Member

    fun findByEmail(email: Email): Member?

}
