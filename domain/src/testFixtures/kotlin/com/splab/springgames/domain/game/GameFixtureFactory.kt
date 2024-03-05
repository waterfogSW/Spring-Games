package com.splab.springgames.domain.game

import com.splab.springgames.domain.game.domain.Game
import com.splab.springgames.domain.game.vo.GameName
import com.splab.springgames.support.common.uuid.UuidGenerator
import java.util.*

object GameFixtureFactory {

    fun create(
        id: UUID = UuidGenerator.create(),
        name: String = "게임"
    ): Game {
        return Game(
            id = id,
            name = GameName(name),
        )
    }
}
