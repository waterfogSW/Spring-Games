package com.splab.springgames.application.member.port.outbound

import com.splab.springgames.domain.member.domain.Member
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MemberEventNotifierSpy : MemberEventNotifier {

    private val bucket = ConcurrentHashMap<UUID, Member>()

    override fun notifyLevelUpdated(member: Member) {
        bucket[member.id] = member
    }

    fun findById(id: UUID): Member? {
        return bucket[id]
    }

    fun clear() {
        bucket.clear()
    }

}
