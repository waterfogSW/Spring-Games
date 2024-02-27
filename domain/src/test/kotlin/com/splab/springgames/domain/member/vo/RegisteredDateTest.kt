package com.splab.springgames.domain.member.vo

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import net.bytebuddy.asm.Advice.Local
import java.time.LocalDateTime

@DisplayName("가입일 테스트")
class RegisteredDateTest : DescribeSpec({

    describe("가입일 생성") {
        listOf(
            LocalDateTime.now(),
            LocalDateTime.now().minusYears(1).plusSeconds(1)
        ).forEach { time ->
            context("성공 - 현재 시간 또는 1년 이내로 입력하면 / time: $time") {
                it("가입일을 생성한다.") {
                    // act
                    val registeredDate: RegisteredDate = RegisteredDate(time)

                    // assert
                    registeredDate.value shouldBe time
                }
            }
        }

        listOf(
            LocalDateTime.now().plusSeconds(1),
            LocalDateTime.now().minusYears(1).minusSeconds(1)
        ).forEach { time ->
            context("실패 - 현재 시간 또는 1년 이내로 입력하지 않으면 / time: $time") {
                it("예외를 던진다.") {
                    // act, assert
                    shouldThrow<IllegalArgumentException> {
                        RegisteredDate(time)
                    }
                }
            }
        }
    }

})
