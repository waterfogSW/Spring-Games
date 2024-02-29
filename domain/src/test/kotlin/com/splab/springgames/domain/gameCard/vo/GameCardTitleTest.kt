package com.splab.springgames.domain.gameCard.vo

import com.splab.springgames.support.common.exception.CustomException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("게임 카드 제목 테스트")
class GameCardTitleTest : DescribeSpec({

    describe("create") {
        context("성공 - 제목이 1자 이상 100자 이하이면") {
            it("게임 카드 제목을 생성한다.") {
                // arrange
                val title = "게임 카드 제목"

                // act
                val gameCardTitle = GameCardTitle.create(title)

                // assert
                gameCardTitle.value shouldBe title
            }
        }

        context("실패 - 제목이 1자 미만이면") {
            it("예외를 발생시킨다.") {
                // act & assert
                shouldThrow<CustomException> {
                    GameCardTitle.create("")
                }
            }
        }

        context("실패 - 제목이 100자 초과이면") {
            it("예외를 발생시킨다.") {
                // arrange
                val title = "A".repeat(101)

                // act & assert
                shouldThrow<CustomException> {
                    GameCardTitle.create(title)
                }
            }
        }
    }


})
