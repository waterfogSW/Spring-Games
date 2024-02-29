package com.splab.springgames.domain.gameCard.vo

import com.splab.springgames.support.common.exception.CustomException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("게임 카드 가격 테스트")
class GameCardPriceTest : DescribeSpec({

    describe("create") {
        listOf(0.toBigDecimal(), 100000.toBigDecimal()).forEach {
            context("성공 - 카드 가격이 0~100,000 범위 내이면 / 가격 : $it") {
                it("게임 카드 가격을 생성한다.") {
                    // act
                    val gameCardPrice = GameCardPrice.create(it)

                    // assert
                    (gameCardPrice.value == it) shouldBe true
                }
            }
        }

        listOf((-1).toBigDecimal(), 100001.toBigDecimal()).forEach {
            context("실패 - 카드 가격이 0~100,000 범위 밖이면 / 가격 : $it") {
                it("예외를 발생시킨다.") {
                    // act & assert
                    shouldThrow<CustomException> {
                        GameCardPrice.create(it)
                    }
                }
            }
        }

        context("성공 - 카드 가격이 소숫점 3자리 이하이면 / 가격 : 100.123") {
            it("2자릿수로 반올림해 가격을 생성한다 / 가격 : 100.12") {
                // act
                val gameCardPrice = GameCardPrice.create(100.123.toBigDecimal())

                // assert
                (gameCardPrice.value == 100.12.toBigDecimal()) shouldBe true
            }
        }

        context("성공 - 카드 가격이 소숫점 3자리이하면 / 가격 : 100.125") {
            it("2자릿수로 반올림해 가격을 생성한다 / 가격 : 100.13") {
                // act
                val gameCardPrice = GameCardPrice.create(100.125.toBigDecimal())

                // assert
                (gameCardPrice.value == 100.13.toBigDecimal()) shouldBe true
            }
        }
    }

})
