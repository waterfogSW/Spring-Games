package com.splab.springgames.bootstrap.ui.member.adapter

import com.splab.springgames.application.member.port.inbound.QueryMemberUseCase
import com.splab.springgames.domain.member.enum.Level
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@Controller
@RequestMapping("/members")
class MemberViewController(
    private val queryMemberUseCase: QueryMemberUseCase,
) {

    @GetMapping
    fun listMembers(
        model: Model,
        @RequestParam(required = false)
        name: String?,
        @RequestParam(required = false)
        level: Level?,
    ): String {
        queryMemberUseCase.searchByFilter(
            name = name,
            level = level,
        ).also {
            model.addAttribute("members", it)
        }
        return "member/list"
    }

    @GetMapping("{id}")
    fun getMember(
        model: Model,
        @PathVariable
        id: UUID,
    ): String {
        queryMemberUseCase
            .getById(id)
            .let { model.addAttribute("member", it) }
        return "member/detail"
    }

    @GetMapping("enrollment")
    fun getEnrollmentForm(): String {
        return "member/enrollment"
    }

    @GetMapping("/{id}/edit")
    fun getEditForm(
        model: Model,
        @PathVariable
        id: UUID,
    ): String {
        queryMemberUseCase
            .getById(id)
            .let { model.addAttribute("member", it) }
        return "member/edit"
    }

}
