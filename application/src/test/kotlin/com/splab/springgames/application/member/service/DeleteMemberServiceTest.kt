package com.splab.springgames.application.member.service

import com.splab.springgames.application.port.outbound.MemberRepositorySpy
import com.splab.springgames.domain.member.MemberFixtureFactory
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("회원 삭제 서비스")
class DeleteMemberServiceTest : DescribeSpec({

    val memberRepositorySpy = MemberRepositorySpy()
    val sut = DeleteMemberService(memberRepositorySpy)

    afterTest {
        memberRepositorySpy.clear()
    }

    describe("회원 삭제") {
        context("ID값에 해당하는 회원이 존재하는 경우") {
            it("해당 ID값에 해당하는 회원을 삭제한다") {
                // arrange
                val member = MemberFixtureFactory.create()
                memberRepositorySpy.save(member)

                // act
                sut.invoke(member.id)

                // assert
                memberRepositorySpy.existsById(member.id) shouldBe false
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
