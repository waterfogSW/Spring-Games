package com.splab.springgames.domain.member.domain

import com.splab.springgames.domain.member.enum.Level
import com.splab.springgames.domain.member.vo.Email
import com.splab.springgames.domain.member.vo.Name
import com.splab.springgames.domain.member.vo.RegisteredDate
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

@DisplayName("회원 도메인 테스트")
class MemberTest : DescribeSpec({

    describe("회원 생성") {
        context("성공 - 요청 값이 유효하면") {
            it("회원을 생성한다.") {
                // arrange
                val name = "홍길동"
                val email = "test1234@test.com"
                val registeredDate = LocalDate.now()

                // act
                val member: Member = Member.create(name, email, registeredDate)

                // assert
                member.name shouldBe Name(name)
                member.email shouldBe Email(email)
                member.registeredDate shouldBe RegisteredDate(registeredDate)
                member.gameCardTotalCount.value shouldBe 0
                member.gameCardTotalPrice.value shouldBe 0.toBigDecimal()
                member.level shouldBe Level.BRONZE
            }
        }
    }

})
