package com.splab.springgames.application.member.service

import com.splab.springgames.application.game.port.outbound.GameCardRepositorySpy
import com.splab.springgames.application.member.port.outbound.GameCardRepository
import com.splab.springgames.application.member.port.outbound.MemberRepositorySpy
import com.splab.springgames.domain.member.GameCardFixtureFactory
import com.splab.springgames.domain.member.MemberFixtureFactory
import com.splab.springgames.domain.member.domain.Member
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("회원 삭제 서비스")
class DeleteMemberServiceTest : DescribeSpec({

    val memberRepositorySpy = MemberRepositorySpy()
    val gameCardRepositorySpy = GameCardRepositorySpy()
    val sut = DeleteMemberService(
        memberRepository = memberRepositorySpy,
        gameCardRepository = gameCardRepositorySpy
    )

    afterTest {
        memberRepositorySpy.clear()
        gameCardRepositorySpy.clear()
    }

    describe("회원 삭제") {
        context("ID값에 해당하는 회원이 존재하는 경우") {
            it("해당 ID값에 해당하는 회원과, 해당 회원의 게임 카드를 삭제한다") {
                // arrange
                val member: Member = MemberFixtureFactory.create()
                    .also { memberRepositorySpy.save(it) }
                GameCardFixtureFactory.create(memberId = member.id)
                    .also { gameCardRepositorySpy.save(it) }
                GameCardFixtureFactory.create(memberId = member.id)
                    .also { gameCardRepositorySpy.save(it) }


                // act
                sut.invoke(member.id)

                // assert
                memberRepositorySpy.existsById(member.id) shouldBe false
                gameCardRepositorySpy.findAllByMemberId(member.id).isEmpty() shouldBe true
            }
        }

        context("ID값에 해당하는 회원이 존재하지 않는 경우") {
            it("아무것도 하지 않는다") {
                // arrange
                val memberId = MemberFixtureFactory.create().id

                // act
                sut.invoke(memberId)

                // assert
                memberRepositorySpy.existsById(memberId) shouldBe false
            }
        }
    }


})
