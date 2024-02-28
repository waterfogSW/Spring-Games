package com.splab.springgames.domain.member

import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.vo.Email
import com.splab.springgames.domain.member.vo.Name
import com.splab.springgames.domain.member.vo.RegisteredDate
import java.time.LocalDateTime

object MemberFixtureFactory {

    fun create(
        name: String = "홍길동",
        email: String = "test",
        registeredDate: LocalDateTime = LocalDateTime.now()
    ): Member {
        return Member.create(
            name = Name(name),
            email = Email(email),
            registeredDate = RegisteredDate(registeredDate)
        )
    }

}
