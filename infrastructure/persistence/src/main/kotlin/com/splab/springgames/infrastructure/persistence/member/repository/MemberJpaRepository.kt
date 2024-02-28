package com.splab.springgames.infrastructure.persistence.member.repository

import com.splab.springgames.infrastructure.persistence.member.entity.MemberJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*


interface MemberJpaRepository : JpaRepository<MemberJpaEntity, UUID> {


    @Query(
        """
            select *
            from member 
            where (:name is null or MATCH(name) against(+:name in boolean mode))
            AND (:level is null or level = :level)
        """,
        nativeQuery = true
    )
    fun searchByFilter(
        name: String?,
        level: String?,
    ): List<MemberJpaEntity>

    fun findByEmail(email: String): MemberJpaEntity?

}
