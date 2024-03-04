package com.splab.springgames.application.member.service.query

import com.splab.springgames.application.member.port.inbound.QueryMemberUseCase
import com.splab.springgames.application.member.port.outbound.MemberRepository
import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.enum.Level
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class QueryMemberService(
    private val memberRepository: MemberRepository,
) : QueryMemberUseCase {

    override fun searchByFilter(
        name: String?,
        level: Level?
    ): List<Member> {
        return memberRepository.searchByFilter(name, level)
    }

    override fun getById(id: UUID): Member? {
        return memberRepository.getById(id)
    }

}
