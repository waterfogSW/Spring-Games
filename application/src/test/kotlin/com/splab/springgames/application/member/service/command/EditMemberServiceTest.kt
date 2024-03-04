package com.splab.springgames.application.member.service.command

import com.splab.springgames.application.member.port.inbound.EditMemberUseCase
import com.splab.springgames.application.member.port.outbound.MemberRepositorySpy
import com.splab.springgames.domain.member.MemberFixtureFactory
import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.exception.MemberExceptionType
import com.splab.springgames.domain.member.vo.Email
import com.splab.springgames.domain.member.vo.Name
import com.splab.springgames.domain.member.vo.RegisteredDate
import com.splab.springgames.support.common.exception.CustomException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

@DisplayName("[Application] 회원 수정 서비스")
class EditMemberServiceTest : DescribeSpec({

    val memberRepositorySpy = MemberRepositorySpy()
    val sut = EditMemberService(memberRepositorySpy)

    afterTest {
        memberRepositorySpy.clear()
    }

    describe("회원 수정") {
        it("[성공] ID값에 해당하는 회원의 정보를 수정한다.") {
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

        context("[실패] 다른 유저가 이미 사용중인 이메일로 수정하면") {
            it("CustomException, DUPLICATED_EMAIL_INPUT 예외를 발생시킨다.") {
                // arrange
                val existsEmail = "test@test.com"
                val targetEmail = "test@naver.com"
                val existsMember: Member = MemberFixtureFactory
                    .create(email = existsEmail)
                    .also { memberRepositorySpy.save(it) }

                MemberFixtureFactory
                    .create(email = targetEmail)
                    .also { memberRepositorySpy.save(it) }

                val command = EditMemberUseCase.Command(
                    memberId = existsMember.id,
                    name = "수정된 이름",
                    email = targetEmail,
                    registeredDate = LocalDate.now()
                )

                // act && assert
                val exception = shouldThrow<CustomException> {
                    sut.invoke(command)
                }
                exception.type shouldBe MemberExceptionType.DUPLICATED_EMAIL_INPUT
            }
        }
    }


})
