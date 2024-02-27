package com.splab.springgames.applicationon.member.port.inbound

import com.splab.springgames.domain.member.vo.Email
import com.splab.springgames.domain.member.vo.Name
import com.splab.springgames.domain.member.vo.RegisteredDate

interface EnrollMemberUseCase {

    fun invoke(command: Command)

    data class Command(
        val name: Name,
        val email: Email,
        val registeredDate: RegisteredDate,
    )

}
