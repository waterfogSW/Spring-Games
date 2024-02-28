package com.splab.springgames.infrastructure.persistence.member.repository

import com.splab.springgames.infrastructure.persistence.member.entity.MemberJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface MemberJpaRepository : JpaRepository<MemberJpaEntity, UUID>
