package com.splab.springgames.application.member.port.outbound

import com.splab.springgames.domain.member.domain.Member
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MemberEventNotifierSpy : MemberEventNotifier {

    private val bucket = ConcurrentHashMap<UUID, Member>()

    override fun notifyLevelUpdated(member: Member) {
        bucket[member.id] = member
    }

    fun count(): Int {
        return bucket.size
    }

    fun clear() {
        bucket.clear()
    }

}
