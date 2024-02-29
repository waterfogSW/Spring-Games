package com.splab.springgames.infrastructure.persistence.game.repository

import com.splab.springgames.infrastructure.persistence.game.entity.GameJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface GameJpaRepository : JpaRepository<GameJpaEntity, UUID>
