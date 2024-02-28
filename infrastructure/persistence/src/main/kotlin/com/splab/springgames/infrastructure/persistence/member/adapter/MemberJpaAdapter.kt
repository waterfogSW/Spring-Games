package com.splab.springgames.infrastructure.persistence.member.adapter

import com.splab.springgames.application.member.port.outbound.MemberRepository
import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.infrastructure.persistence.member.entity.MemberJpaEntity.Companion.toJpaEntity
import com.splab.springgames.infrastructure.persistence.member.repository.MemberJpaRepository
import org.springframework.stereotype.Component

@Component
class MemberJpaAdapter(
    private val memberJpaRepository: MemberJpaRepository
) : MemberRepository {

    override fun save(member: Member) {
        member
            .toJpaEntity()
            .also { memberJpaRepository.save(it) }
    }

}
