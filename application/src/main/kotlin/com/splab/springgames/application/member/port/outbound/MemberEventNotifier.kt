package com.splab.springgames.application.member.port.outbound

import com.splab.springgames.domain.member.domain.Member
import org.springframework.scheduling.annotation.Async
import java.util.concurrent.CompletableFuture

interface MemberEventNotifier {

    @Async
    fun notifyLevelUpdated(member: Member): CompletableFuture<Unit>

}
