package com.splab.springgames.bootstrap.ui.common.adapter

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class CommonViewController {

    @GetMapping
    fun redirectToMembers(): String {
        return "redirect:/members"
    }

}
