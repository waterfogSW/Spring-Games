package com.splab.springgames.domain.member.vo

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("회원 이름 테스트")
class NameTest : DescribeSpec({

    describe("회원 이름 생성") {
        (2..100).forEach { n ->
            context("성공 - 이름이 2~100 글자 이내면 / $n 글자") {
                it("회원 이름을 생성한다.") {
                    // arrange
                    val nameValue = "a".repeat(n)

                    // act
                    val result = Name(nameValue)

                    // assert
                    result.value shouldBe "a".repeat(n)
                }
            }
        }

        listOf(0, 1, 101).forEach { n ->
            context("실패 - 이름이 2~100 글자가 아니면 / $n 글자") {
                it("예외를 던진다.") {
                    // arrange
                    val nameValue = "a".repeat(n)

                    // act, assert
                    shouldThrow<IllegalArgumentException> {
                        Name(nameValue)
                    }
                }
            }
        }
    }

})
