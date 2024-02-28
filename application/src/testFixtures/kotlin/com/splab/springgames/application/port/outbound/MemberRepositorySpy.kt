package com.splab.springgames.application.port.outbound

import com.splab.springgames.application.member.port.outbound.MemberRepository
import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.enum.Level
import com.splab.springgames.domain.member.vo.Email
import com.splab.springgames.domain.member.vo.Name
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MemberRepositorySpy : MemberRepository {

    private val bucket = ConcurrentHashMap<UUID, Member>()

    override fun save(member: Member) {
        bucket[member.id] = member
    }

    override fun searchByFilter(
        name: Name?,
        level: Level?
    ): List<Member> {
        return bucket.values
            .filter { name == null || it.name == name }
            .filter { level == null || it.level == level }

    }


    fun findByEmail(email: Email): Member? {
        return bucket.values.find { it.email == email }
    }

    fun clear() {
        bucket.clear()
    }

}
