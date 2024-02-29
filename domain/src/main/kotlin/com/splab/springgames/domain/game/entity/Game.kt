package com.splab.springgames.domain.game.entity

import com.splab.springgames.domain.game.vo.GameName
import java.util.*


data class Game(
    val id: UUID,
    val name: GameName,
)
