package com.splab.springgames.domain.member

import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.enum.Level
import com.splab.springgames.domain.member.vo.Email
import com.splab.springgames.domain.member.vo.GameCardTotalCount
import com.splab.springgames.domain.member.vo.GameCardTotalPrice
import com.splab.springgames.domain.member.vo.Name
import com.splab.springgames.domain.member.vo.RegisteredDate
import java.time.LocalDateTime

object MemberFixtureFactory {

    fun create(
        name: String = "홍길동",
        email: String = "test@test.com",
        registeredDate: LocalDateTime = LocalDateTime.now(),
        level: Level = Level.BROZNE,
        gameCardTotalCount: GameCardTotalCount = GameCardTotalCount(0),
        gameCardTotalPrice: GameCardTotalPrice = GameCardTotalPrice(0.toBigDecimal()),
    ): Member {
        return Member(
            name = Name(name),
            email = Email(email),
            registeredDate = RegisteredDate(registeredDate),
            level = level,
            gameCardTotalCount = gameCardTotalCount,
            gameCardTotalPrice = gameCardTotalPrice,
        )
    }

}
