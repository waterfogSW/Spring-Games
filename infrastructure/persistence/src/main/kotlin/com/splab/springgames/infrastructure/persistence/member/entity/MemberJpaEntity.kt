package com.splab.springgames.infrastructure.persistence.member.entity

import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.enum.Level
import com.splab.springgames.domain.member.vo.Email
import com.splab.springgames.domain.member.vo.GameCardTotalCount
import com.splab.springgames.domain.member.vo.GameCardTotalPrice
import com.splab.springgames.domain.member.vo.Name
import com.splab.springgames.domain.member.vo.RegisteredDate
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "member")
class MemberJpaEntity(
    id: UUID,
    name: String,
    email: String,
    registeredDate: LocalDateTime,
    level: Level,
    gameCardTotalCount: Int,
    gameCardTotalPrice: Int,
) {

    @Id
    var id: UUID = id
        private set

    @Column(nullable = false, updatable = true)
    var name: String = name
        private set

    @Column(nullable = false, updatable = true)
    var email: String = email
        private set

    @Column(nullable = false, updatable = true)
    var registeredDate: LocalDateTime = registeredDate
        private set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = true, columnDefinition = "varchar(255)")
    var level: Level = level
        private set

    @Column(nullable = false, updatable = true)
    var gameCardTotalCount: Int = gameCardTotalCount
        private set

    @Column(nullable = false, updatable = true)
    var gameCardTotalPrice: Int = gameCardTotalPrice
        private set

    fun toDomain(): Member {
        return Member(
            id = id,
            name = Name(name),
            email = Email(email),
            registeredDate = RegisteredDate(registeredDate),
            level = level,
            gameCardTotalCount = GameCardTotalCount(gameCardTotalCount),
            gameCardTotalPrice = GameCardTotalPrice(gameCardTotalPrice),
        )
    }

    companion object {
        fun Member.toJpaEntity(): MemberJpaEntity {
            return MemberJpaEntity(
                id = id,
                name = name.value,
                email = email.value,
                registeredDate = registeredDate.value,
                level = level,
                gameCardTotalCount = gameCardTotalCount.value,
                gameCardTotalPrice = gameCardTotalPrice.value,
            )
        }
    }

}
