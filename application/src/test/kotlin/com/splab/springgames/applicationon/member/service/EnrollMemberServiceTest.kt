package com.splab.springgames.applicationon.member.service

import com.splab.springgames.application.port.outbound.MemberRepositorySpy
import com.splab.springgames.applicationon.member.port.inbound.EnrollMemberUseCase
import com.splab.springgames.domain.member.vo.Email
import com.splab.springgames.domain.member.vo.Name
import com.splab.springgames.domain.member.vo.RegisteredDate
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.LocalDateTime

@DisplayName("회원 등록 서비스 테스트")
class EnrollMemberServiceTest : DescribeSpec({

    val memberRepositorySpy = MemberRepositorySpy()
    val sut = EnrollMemberService(memberRepositorySpy)

    describe("회원 등록 서비스") {
        it("새로운 회원을 생성하고 저장한다") {
            // arrange
            val name = Name("홍길동")
            val email = Email("test@test.com")
            val registeredDate = RegisteredDate(LocalDateTime.now())
            val command: EnrollMemberUseCase.Command = EnrollMemberUseCase.Command(
                name = name,
                email = email,
                registeredDate = registeredDate
            )

            // act
            sut.invoke(command)

            // assert
            memberRepositorySpy.findByEmail(email) shouldNotBe null
        }
    }

})
