package com.splab.springgames.application.member.service.command

import com.splab.springgames.application.member.port.inbound.EnrollMemberUseCase
import com.splab.springgames.application.member.port.outbound.MemberEventNotifierSpy
import com.splab.springgames.application.member.port.outbound.MemberRepositorySpy
import com.splab.springgames.domain.member.vo.Email
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.LocalDate

@DisplayName("회원 등록 서비스 테스트")
class EnrollMemberServiceTest : DescribeSpec({

    val memberRepositorySpy = MemberRepositorySpy()
    val memberEventNotifier = MemberEventNotifierSpy()
    val sut = EnrollMemberService(
        memberRepository = memberRepositorySpy,
        memberEventNotifier = memberEventNotifier
    )

    afterTest {
        memberRepositorySpy.clear()
        memberEventNotifier.clear()
    }

    describe("회원 등록 서비스") {
        it("새로운 회원을 생성, 저장하고 알림을 보낸다.") {
            // arrange
            val name = "홍길동"
            val email = "test@test.com"
            val registeredDate = LocalDate.now()
            val command: EnrollMemberUseCase.Command = EnrollMemberUseCase.Command(
                name = name,
                email = email,
                registeredDate = registeredDate
            )

            // act
            sut.invoke(command)

            // assert
            memberRepositorySpy.findByEmail(Email(email)) shouldNotBe null
            memberEventNotifier.count() shouldBe 1
        }
    }

})
