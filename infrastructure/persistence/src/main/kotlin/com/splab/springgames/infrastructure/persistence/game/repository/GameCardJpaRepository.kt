package com.splab.springgames.infrastructure.persistence.game.repository

import com.splab.springgames.infrastructure.persistence.game.entity.GameCardJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*

interface GameCardJpaRepository : JpaRepository<GameCardJpaEntity, UUID> {

    fun findAllByMemberId(memberId: UUID): List<GameCardJpaEntity>

    fun existsByGameIdAndSerialNumber(
        gameId: UUID,
        serialNumber: Long
    ): Boolean

    @Modifying
    @Query("DELETE FROM GameCardJpaEntity g WHERE g.memberId = :memberId")
    fun deleteAllByMemberId(memberId: UUID)

}
