package com.splab.springgames.infrastructure.persistence.ui.adapter

import com.splab.springgames.application.member.port.inbound.QueryMemberUseCase
import com.splab.springgames.domain.member.enum.Level
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@Controller
class MemberController(
    private val memberUseCase: QueryMemberUseCase,
) {

    @GetMapping("/members")
    fun listMembers(
        model: Model,
        @RequestParam(required = false)
        name: String?,
        @RequestParam(required = false)
        level: Level?,
    ): String {
        memberUseCase.searchByFilter(
            name = name,
            level = level,
        ).let {
            model.addAttribute("members", it)
        }
        return "members"
    }

    @GetMapping("/members/{id}")
    fun getMember(
        model: Model,
        @PathVariable
        id: UUID,
    ): String {
        memberUseCase
            .getById(id)
            .let { model.addAttribute("member", it) }
        return "member_detail"

    }


}
