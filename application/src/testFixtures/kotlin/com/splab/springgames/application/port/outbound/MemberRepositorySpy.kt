package com.splab.springgames.application.port.outbound

import com.splab.springgames.applicationon.member.port.outbound.MemberRepository
import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.vo.Email
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

class MemberRepositorySpy : MemberRepository {

    private val bucket = ConcurrentHashMap<UUID, Member>()

    override fun save(member: Member) {
        bucket[member.id] = member
    }

    fun findByEmail(email: Email): Member? {
        return bucket.values.find { it.email == email }
    }

    fun clear() {
        bucket.clear()
    }

}
