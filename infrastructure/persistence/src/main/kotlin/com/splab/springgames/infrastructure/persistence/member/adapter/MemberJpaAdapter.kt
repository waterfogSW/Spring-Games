package com.splab.springgames.infrastructure.persistence.member.adapter

import com.splab.springgames.application.member.port.outbound.MemberRepository
import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.enum.Level
import com.splab.springgames.domain.member.vo.Email
import com.splab.springgames.infrastructure.persistence.member.entity.MemberJpaEntity.Companion.toJpaEntity
import com.splab.springgames.infrastructure.persistence.member.repository.MemberJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class MemberJpaAdapter(
    private val memberJpaRepository: MemberJpaRepository
) : MemberRepository {

    override fun save(member: Member) {
        member
            .toJpaEntity()
            .also { memberJpaRepository.save(it) }
    }

    override fun searchByFilter(
        name: String?,
        level: Level?,
    ): List<Member> {
        return memberJpaRepository
            .searchByFilter(
                name = name,
                level = level?.name
            )
            .map { it.toDomain() }
    }

    override fun getById(id: UUID): Member {
        return memberJpaRepository
            .findById(id)
            .map { it.toDomain() }
            .orElseThrow { NoSuchElementException("ID=${id} 에 해당하는 회원이 없습니다") }
    }

    override fun findByEmail(email: Email): Member? {
        return memberJpaRepository
            .findByEmail(email.value)
            ?.toDomain()
    }


}
