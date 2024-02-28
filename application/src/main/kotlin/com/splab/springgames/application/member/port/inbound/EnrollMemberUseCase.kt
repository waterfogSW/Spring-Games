package com.splab.springgames.application.member.port.inbound

import java.time.LocalDateTime

interface EnrollMemberUseCase {

    fun invoke(command: Command)

    data class Command(
        val name: String,
        val email: String,
        val registeredDate: LocalDateTime,
    )

}
