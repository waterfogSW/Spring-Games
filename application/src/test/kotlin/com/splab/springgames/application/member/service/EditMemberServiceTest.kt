package com.splab.springgames.application.member.service

import com.splab.springgames.application.member.port.inbound.EditMemberUseCase
import com.splab.springgames.application.member.port.outbound.MemberRepositorySpy
import com.splab.springgames.domain.member.MemberFixtureFactory
import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.vo.Email
import com.splab.springgames.domain.member.vo.Name
import com.splab.springgames.domain.member.vo.RegisteredDate
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

@DisplayName("회원 수정 서비스 테스트")
class EditMemberServiceTest : DescribeSpec({

    val memberRepositorySpy = MemberRepositorySpy()
    val sut = EditMemberService(memberRepositorySpy)

    afterTest {
        memberRepositorySpy.clear()
    }

    describe("회원 수정 서비스") {
        it("ID값에 해당하는 회원의 정보를 수정한다.") {
            // arrange
            val existsMember: Member = MemberFixtureFactory.create()
            memberRepositorySpy.save(existsMember)

            val updateName = "수정된 이름"
            val updateEmail = "testtest@testtest.com"
            val updateRegisteredDate = LocalDate.now()

            val command = EditMemberUseCase.Command(
                memberId = existsMember.id,
                name = updateName,
                email = updateEmail,
                registeredDate = updateRegisteredDate
            )

            // act
            sut.invoke(command)

            // assert
            val updatedMember = memberRepositorySpy.getById(existsMember.id)
            updatedMember.name shouldBe Name(updateName)
            updatedMember.email shouldBe Email(updateEmail)
            updatedMember.registeredDate shouldBe RegisteredDate(updateRegisteredDate)
        }
    }


})
