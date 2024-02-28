package com.splab.springgames.bootstrap.ui.member.adapter

import com.splab.springgames.application.member.port.inbound.EnrollMemberUseCase
import com.splab.springgames.bootstrap.ui.member.dto.EnrollMemberRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/members")
class MemberRestController (
    private val enrollMemberUseCase: EnrollMemberUseCase,
){

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun enrollMember(
        @RequestBody
        request: EnrollMemberRequest,
    ) {
        request
            .toCommand()
            .also { enrollMemberUseCase.invoke(it) }
    }

}
