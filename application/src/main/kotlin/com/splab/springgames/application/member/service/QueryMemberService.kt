package com.splab.springgames.application.member.service

import com.splab.springgames.application.member.port.inbound.QueryMemberUseCase
import com.splab.springgames.application.member.port.outbound.MemberRepository
import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.enum.Level
import com.splab.springgames.domain.member.vo.Name
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryMemberService(
    private val memberRepository: MemberRepository,
) : QueryMemberUseCase {

    @Transactional(readOnly = true)
    override fun searchByFilter(
        name: Name?,
        level: Level?
    ): List<Member> {
        return memberRepository.searchByFilter(name, level)
    }

}
