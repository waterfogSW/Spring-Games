package com.splab.springgames.infrastructure.persistence.gameCard.repository

import com.splab.springgames.infrastructure.persistence.gameCard.entity.GameCardJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GameCardJpaRepository : JpaRepository<GameCardJpaEntity, UUID>
