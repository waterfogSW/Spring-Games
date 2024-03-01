package com.splab.springgames.bootstrap.ui.gameCard.adapter

import com.splab.springgames.application.gameCard.port.inbound.AddGameCardUseCase
import com.splab.springgames.bootstrap.ui.gameCard.dto.AddGameCardRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/members/{memberId}/game-cards")
class GameCardRestController(
    private val addGameCardUseCase: AddGameCardUseCase,
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addGameCard(
        @PathVariable
        memberId: UUID,
        @RequestBody
        request: AddGameCardRequest,
    ) {
        request
            .toCommandWith(memberId)
            .also { addGameCardUseCase.invoke(it) }
    }


}
