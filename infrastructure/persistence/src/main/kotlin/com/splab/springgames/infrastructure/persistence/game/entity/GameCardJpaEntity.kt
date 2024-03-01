package com.splab.springgames.infrastructure.persistence.game.entity

import com.splab.springgames.domain.member.domain.GameCard
import com.splab.springgames.domain.member.vo.GameCardPrice
import com.splab.springgames.domain.member.vo.GameCardSerialNumber
import com.splab.springgames.domain.member.vo.GameCardTitle
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "game_card")
class GameCardJpaEntity(
    id: UUID,
    memberId: UUID,
    gameId: UUID,
    title: String,
    serialNumber: Long,
    price: BigDecimal,
) {

    @Id
    @Column(nullable = false, updatable = false)
    var id: UUID = id
        private set

    @Column(nullable = false, updatable = false)
    var memberId: UUID = memberId
        private set

    @Column(nullable = false, updatable = false)
    var gameId: UUID = gameId
        private set

    @Column(nullable = false, updatable = false)
    var title: String = title
        private set

    @Column(nullable = false, updatable = false)
    var serialNumber: Long = serialNumber
        private set

    @Column(nullable = false, updatable = false)
    var price: BigDecimal = price
        private set

    fun toDomain(): GameCard {
        return GameCard(
            id = id,
            memberId = memberId,
            gameId = gameId,
            title = GameCardTitle(title),
            serialNumber = GameCardSerialNumber(serialNumber),
            price = GameCardPrice(price),
        )
    }

    companion object {

        fun GameCard.toJpaEntity(): GameCardJpaEntity {
            return GameCardJpaEntity(
                id = id,
                memberId = memberId,
                gameId = gameId,
                title = title.value,
                serialNumber = serialNumber.value,
                price = price.value,
            )
        }

    }

}
