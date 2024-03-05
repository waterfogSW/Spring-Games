package com.splab.springgames.application.member.port.outbound

import com.splab.springgames.domain.member.domain.Member
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

class MemberEventNotifierSpy : MemberEventNotifier {

    private val bucket = ConcurrentHashMap<UUID, Member>()

    override fun notifyLevelUpdated(member: Member): CompletableFuture<Unit> {
        bucket[member.id] = member
        return CompletableFuture.completedFuture(Unit)
    }

    fun count(): Int {
        return bucket.size
    }

    fun clear() {
        bucket.clear()
    }

}
