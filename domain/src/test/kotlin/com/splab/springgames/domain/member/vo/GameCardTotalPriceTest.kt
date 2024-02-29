package com.splab.springgames.domain.member.vo

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("게임 카드 총 가격 테스트")
class GameCardTotalPriceTest : DescribeSpec({

    describe("게임 카드 총 가격 생성") {
        context("성공 - 요청 값이 유효하면") {
            it("게임 카드 총 가격을 생성한다.") {
                // arrange
                val value = 0

                // act
                val gameCardTotalPrice = GameCardTotalPrice(value.toBigDecimal())

                // assert
                gameCardTotalPrice.value shouldBe value.toBigDecimal()
            }
        }

        context("실패 - 요청 값이 유효하지 않으면") {
            it("예외를 발생시킨다.") {
                // arrange
                val value = -1

                // act & assert
                shouldThrow<IllegalArgumentException> {
                    GameCardTotalPrice(value.toBigDecimal())
                }
            }
        }
    }


})
