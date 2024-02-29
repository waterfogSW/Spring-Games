package com.splab.springgames.domain.game.vo

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("게임 이름")
class GameNameTest : DescribeSpec({

    describe("게임이름 생성자") {
        (1..100).forEach { length ->
            context("성공 - 게임 이름이 1~100자 이내인 경우 / $length 글자") {
                it("게임 이름을 생성한다") {
                    val gameName = GameName("a".repeat(length))

                    gameName.value shouldBe "a".repeat(length)
                }
            }
        }

        listOf(0, 101).forEach {
            context("실패 - 게임 이름이 1~100자 이내가 아닌 경우 / $it 글자") {
                it("예외를 던진다") {
                    shouldThrow<IllegalArgumentException> {
                        GameName("a".repeat(it))
                    }
                }
            }
        }
    }

})
