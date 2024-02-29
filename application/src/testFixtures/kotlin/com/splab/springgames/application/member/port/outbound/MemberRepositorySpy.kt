package com.splab.springgames.application.member.port.outbound

import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.enum.Level
import com.splab.springgames.domain.member.vo.Email
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MemberRepositorySpy : MemberRepository {

    private val bucket = ConcurrentHashMap<UUID, Member>()

    override fun save(member: Member) {
        bucket[member.id] = member
    }

    override fun searchByFilter(
        name: String?,
        level: Level?
    ): List<Member> {
        val nameFilter = { elementName: String? ->
            if (name == null) true
            else elementName?.contains(name, ignoreCase = true) ?: false
        }

        val levelFilter = { elementLevel: Level? ->
            if (level == null) true
            else elementLevel == level
        }

        return bucket.values.filter { nameFilter(it.name.value) && levelFilter(it.level) }
    }

    override fun existsById(id: UUID): Boolean {
        return bucket.containsKey(id)
    }

    override fun getById(id: UUID): Member {
        return bucket[id] ?: throw NoSuchElementException()
    }

    override fun findByEmail(email: Email): Member? {
        return bucket.values.find { it.email == email }
    }

    override fun deleteById(id: UUID) {
        bucket.remove(id)
    }

    fun clear() {
        bucket.clear()
    }

}
