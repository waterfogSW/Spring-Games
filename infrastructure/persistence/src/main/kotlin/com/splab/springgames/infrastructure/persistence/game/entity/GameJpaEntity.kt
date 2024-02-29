package com.splab.springgames.infrastructure.persistence.game.entity

import com.splab.springgames.domain.game.entity.Game
import com.splab.springgames.domain.game.vo.GameName
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "game")
class GameJpaEntity(
    id: UUID,
    name: String,
) {

    @Id
    var id: UUID = id
        private set

    @Column(nullable = false, updatable = false)
    var name: String = name
        private set

    fun toDomain(): Game {
        return Game(
            id = id,
            name = GameName(name),
        )
    }

}
