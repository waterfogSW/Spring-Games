package com.splab.springgames.domain.gameCard.vo

import com.splab.springgames.support.common.exception.CustomException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GameCardSerialNumberTest : DescribeSpec({

    describe("create") {
        context("성공 - 시리얼 번호가 1 이상이면 / 시리얼 번호 : 1") {
            it("게임 카드 시리얼 번호를 생성한다.") {
                // act
                val gameCardSerialNumber = GameCardSerialNumber.create(1)

                // assert
                gameCardSerialNumber.value shouldBe 1
            }
        }

        context("실패 - 시리얼 번호가 1 미만이면 / 시리얼 번호 : 0") {
            it("예외를 발생시킨다.") {
                // act & assert
                shouldThrow<CustomException> {
                    GameCardSerialNumber.create(0)
                }
            }
        }
    }


})
