package com.splab.springgames.domain.member.vo

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("이메일 테스트")
class EmailTest : DescribeSpec({

    describe("이메일 생성") {
        context("성공 - 올바른 이메일 형식으로 입력하면") {
            it("이메일을 생성한다.") {
                // arrange
                val emailValue = "test@test.com"

                // act, assert
                Email(emailValue).value shouldBe emailValue
            }
        }

        context("실패 - 올바르지 않은 이메일 형식으로 입력하면") {
            it("예외를 던진다.") {
                // arrange
                val emailValue = "test"

                // act, assert
                shouldThrow<IllegalArgumentException> {
                    Email(emailValue)
                }
            }
        }
    }

})
