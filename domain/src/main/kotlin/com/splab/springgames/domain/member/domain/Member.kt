package com.splab.springgames.domain.member.domain

import com.splab.springgames.domain.member.enum.Level
import com.splab.springgames.domain.member.vo.Email
import com.splab.springgames.domain.member.vo.GameCardTotalCount
import com.splab.springgames.domain.member.vo.GameCardTotalPrice
import com.splab.springgames.domain.member.vo.Name
import com.splab.springgames.domain.member.vo.RegisteredDate
import com.splab.springgames.support.common.uuid.UuidGenerator
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

    companion object {

        fun create(
            name: Name,
            email: Email,
            registeredDate: RegisteredDate,
        ): Member {
            return Member(
                name = name,
                email = email,
                registeredDate = registeredDate,
                level = Level.BRONZE,
                gameCardTotalCount = GameCardTotalCount(0),
                gameCardTotalPrice = GameCardTotalPrice(0.toBigDecimal())
            )
        }

    }

}
