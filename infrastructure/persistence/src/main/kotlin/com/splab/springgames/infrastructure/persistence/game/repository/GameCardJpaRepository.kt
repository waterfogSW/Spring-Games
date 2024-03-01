package com.splab.springgames.infrastructure.persistence.game.repository

import com.splab.springgames.infrastructure.persistence.game.entity.GameCardJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GameCardJpaRepository : JpaRepository<GameCardJpaEntity, UUID> {

    fun findAllByMemberId(memberId: UUID): List<GameCardJpaEntity>

}
