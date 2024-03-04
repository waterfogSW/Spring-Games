package com.splab.springgames.domain.member.domain

import com.splab.springgames.domain.member.enum.Level
import com.splab.springgames.domain.member.vo.Email
import com.splab.springgames.domain.member.vo.GameCardTotalCount
import com.splab.springgames.domain.member.vo.GameCardTotalPrice
import com.splab.springgames.domain.member.vo.Name
import com.splab.springgames.domain.member.vo.RegisteredDate
import com.splab.springgames.support.common.uuid.UuidGenerator
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*


data class Member(
    val id: UUID = UuidGenerator.create(),
    val name: Name,
    val email: Email,
    val registeredDate: RegisteredDate,
    val level: Level,
    val gameCardTotalCount: GameCardTotalCount,
    val gameCardTotalPrice: GameCardTotalPrice,
) {

    fun addGameCard(gameCard: GameCard): Member {
        return this.copy(
            gameCardTotalCount = GameCardTotalCount(this.gameCardTotalCount.value + 1),
            gameCardTotalPrice = GameCardTotalPrice(this.gameCardTotalPrice.value + gameCard.price.value)
        )
    }

    fun deleteGameCard(gameCard: GameCard): Member {
        return this.copy(
            gameCardTotalCount = GameCardTotalCount(this.gameCardTotalCount.value - 1),
            gameCardTotalPrice = GameCardTotalPrice(this.gameCardTotalPrice.value - gameCard.price.value)
        )
    }

    fun update(
        name: String,
        email: String,
        registeredDate: LocalDate,
    ): Member {
        return this.copy(
            name = Name.create(name),
            email = Email.create(email),
            registeredDate = RegisteredDate.create(registeredDate)
        )
    }

    fun updateLevelWith(memberGameCards: List<GameCard>): Member {
        return memberGameCards
            .filter { it.isValidCard() }
            .let { this.copy(level = determineMemberLevel(it)) }
    }

    private fun determineMemberLevel(validGameCards: List<GameCard>): Level {
        return when {
            meetsGoldLevelConditions(validGameCards) -> Level.GOLD
            meetsSilverLevelConditions(validGameCards) -> Level.SILVER
            else -> Level.BRONZE
        }
    }

    private fun meetsGoldLevelConditions(validGameCards: List<GameCard>): Boolean {
        val distinctGamesCount = validGameCards.map { it.gameId }.distinct().count()
        val totalValidPrice = validGameCards.sumOf { it.price.value }

        val condition1 = validGameCards.size >= 4
        val condition2 = (totalValidPrice >= BigDecimal(100) && validGameCards.size in 2..3)
        val condition3 = distinctGamesCount >= 2
        return (condition1 or condition2) and condition3
    }

    private fun meetsSilverLevelConditions(validGameCards: List<GameCard>): Boolean {
        return validGameCards.isNotEmpty()
    }

    companion object {

        fun create(
            name: String,
            email: String,
            registeredDate: LocalDate,
        ): Member {
            return Member(
                name = Name.create(name),
                email = Email.create(email),
                registeredDate = RegisteredDate.create(registeredDate),
                level = Level.BRONZE,
                gameCardTotalCount = GameCardTotalCount(0),
                gameCardTotalPrice = GameCardTotalPrice(0.toBigDecimal())
            )
        }
    }

}
