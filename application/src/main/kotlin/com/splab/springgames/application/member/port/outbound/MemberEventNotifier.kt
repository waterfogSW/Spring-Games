package com.splab.springgames.application.member.port.outbound

import com.splab.springgames.domain.member.domain.Member

interface MemberEventNotifier {

    fun notifyLevelUpdated(member: Member)

}
