package com.splab.springgames.application.member.port.inbound

import java.time.LocalDate

interface EnrollMemberUseCase {

    fun invoke(command: Command)

    data class Command(
        val name: String,
        val email: String,
        val registeredDate: LocalDate,
    )

}
