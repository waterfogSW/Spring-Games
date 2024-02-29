package com.splab.springgames.application.member.port.inbound

import java.time.LocalDate
import java.util.*

interface EditMemberUseCase {

    fun invoke(command: Command)

    data class Command(
        val memberId: UUID,
        val name: String,
        val email: String,
        val registeredDate: LocalDate,
    )

}
