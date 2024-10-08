package com.splab.springgames.domain.member.domain

import com.splab.springgames.support.common.uuid.UuidGenerator
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("[Domain] 게임 카드")
class GameCardTest : DescribeSpec({

    describe("create") {
        context("성공 - 게임 카드를 생성한다.") {
            it("게임 카드를 생성한다.") {
                // arrange
                val gameId = UuidGenerator.create()
                val memberId = UuidGenerator.create()
                val title = "게임 카드 제목"
                val serialNumber = 1L
                val price = 1000.toBigDecimal()

                // act
                val gameCard = GameCard.create(
                    gameId = gameId,
                    userId = memberId,
                    title = title,
                    serialNumber = serialNumber,
                    price = price,
                )

                // assert
                gameCard.gameId shouldBe gameId
                gameCard.memberId shouldBe memberId
                gameCard.title.value shouldBe title
                (gameCard.serialNumber.value == serialNumber) shouldBe true
                gameCard.price.value shouldBe price
            }
        }
    }

    describe("isValid") {
        context("게임 카드의 가격이 0이면") {
            it("false 를 반환한다") {
                // arrange
                val gameId = UuidGenerator.create()
                val memberId = UuidGenerator.create()
                val title = "게임 카드 제목"
                val serialNumber = 1L
                val price = 0.toBigDecimal()
                val gameCard = GameCard.create(
                    gameId = gameId,
                    userId = memberId,
                    title = title,
                    serialNumber = serialNumber,
                    price = price,
                )

                // act
                val result = gameCard.isValidCard()

                // assert
                result shouldBe false
            }
        }

        context("게임 카드의 가격이 0이 아니면") {
            it("true 를 반환한다") {
                // arrange
                val gameId = UuidGenerator.create()
                val memberId = UuidGenerator.create()
                val title = "게임 카드 제목"
                val serialNumber = 1L
                val price = 1000.toBigDecimal()
                val gameCard = GameCard.create(
                    gameId = gameId,
                    userId = memberId,
                    title = title,
                    serialNumber = serialNumber,
                    price = price,
                )

                // act
                val result = gameCard.isValidCard()

                // assert
                result shouldBe true
            }
        }
    }

})
