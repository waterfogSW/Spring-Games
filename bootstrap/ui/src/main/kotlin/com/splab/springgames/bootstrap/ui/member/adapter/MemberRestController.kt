package com.splab.springgames.bootstrap.ui.member.adapter

import com.splab.springgames.application.member.port.inbound.EditMemberUseCase
import com.splab.springgames.application.member.port.inbound.EnrollMemberUseCase
import com.splab.springgames.bootstrap.ui.member.dto.EditMemberRequest
import com.splab.springgames.bootstrap.ui.member.dto.EnrollMemberRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/members")
class MemberRestController(
    private val enrollMemberUseCase: EnrollMemberUseCase,
    private val editMemberUseCase: EditMemberUseCase,
) {

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

    @PutMapping("/{memberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun editMember(
        @PathVariable
        memberId: UUID,
        @RequestBody
        request: EditMemberRequest
    ) {
        request
            .toCommandWith(memberId)
            .also { editMemberUseCase.invoke(it) }
    }

}
