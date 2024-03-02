package com.splab.springgames.domain.member.domain

import com.splab.springgames.domain.member.GameCardFixtureFactory
import com.splab.springgames.domain.member.MemberFixtureFactory
import com.splab.springgames.domain.member.enum.Level
import com.splab.springgames.domain.member.vo.Email
import com.splab.springgames.domain.member.vo.GameCardTotalCount
import com.splab.springgames.domain.member.vo.GameCardTotalPrice
import com.splab.springgames.domain.member.vo.Name
import com.splab.springgames.domain.member.vo.RegisteredDate
import com.splab.springgames.support.common.uuid.UuidGenerator
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.util.UUID

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

    describe("회원 수정") {
        context("성공 - 요청 값이 유효하면") {
            it("회원을 수정한다.") {
                // arrange
                val member: Member = MemberFixtureFactory.create()
                val updateName = "김철수"
                val updateEmail = "msa@naver.com"
                val updateRegisteredDate: LocalDate = LocalDate.now().minusDays(1)

                // act
                val updatedMember: Member = member.update(updateName, updateEmail, updateRegisteredDate)

                // assert
                updatedMember.name shouldBe Name(updateName)
                updatedMember.email shouldBe Email(updateEmail)
                updatedMember.registeredDate shouldBe RegisteredDate(updateRegisteredDate)
            }
        }
    }

    describe("회원 카드 추가") {
        context("성공 - 요청 값이 유효하면") {
            it("회원이 가지고 있는 총 카드 개수와 가격을 업데이트 한다.") {
                // arrange
                val member: Member = MemberFixtureFactory.create()
                val gameCard: GameCard = GameCardFixtureFactory.create()

                // act
                val updatedMember: Member = member.addGameCard(gameCard)

                // assert
                updatedMember.gameCardTotalCount.value shouldBe 1
                updatedMember.gameCardTotalPrice.value shouldBe gameCard.price.value
            }
        }
    }

    describe("회원 카드 삭제") {
        context("성공 - 요청 값이 유효하면") {
            it("회원이 가지고 있는 총 카드 개수와 가격을 업데이트 한다.") {
                // arrange
                val existsGameCardTotalCount = GameCardTotalCount(3)
                val existsGameCardTotalPrice = GameCardTotalPrice(30.toBigDecimal())
                val member: Member = MemberFixtureFactory.create(
                    gameCardTotalCount = existsGameCardTotalCount,
                    gameCardTotalPrice = existsGameCardTotalPrice
                )

                val deleteGameCardPrice = 10.toBigDecimal()
                val gameCard: GameCard = GameCardFixtureFactory.create(
                    price = deleteGameCardPrice
                )

                // act
                val updated: Member = member.deleteGameCard(gameCard)

                // assert
                updated.gameCardTotalCount.value shouldBe existsGameCardTotalCount.value - 1
                updated.gameCardTotalPrice.value shouldBe existsGameCardTotalPrice.value - deleteGameCardPrice
            }
        }
    }

    describe("회원 레벨 업데이트") {
        context("회원의 유효 게임카드가 4장 이상이고, 2개 이상의 게임을 가지고 있으면") {
            it("골드 레벨로 업데이트 한다.") {
                // arrange
                val member: Member = MemberFixtureFactory.create(level = Level.BRONZE)
                val gameId1: UUID = UuidGenerator.create()
                val gameId2: UUID = UuidGenerator.create()
                val gameCards: List<GameCard> = listOf(
                    GameCardFixtureFactory.create(gameId = gameId1, price = 10.toBigDecimal()),
                    GameCardFixtureFactory.create(gameId = gameId1, price = 10.toBigDecimal()),
                    GameCardFixtureFactory.create(gameId = gameId2, price = 10.toBigDecimal()),
                    GameCardFixtureFactory.create(gameId = gameId2, price = 10.toBigDecimal()),
                )

                // act
                val updatedMember: Member = member.updateLevelWith(gameCards)

                // assert
                updatedMember.level shouldBe Level.GOLD
            }
        }

        context("가격의 합계가 100 이상인 유효카드를 2~3개 가지고 있으면") {
            it("골드 레벨로 업데이트 한다.") {
                // arrange
                val member: Member = MemberFixtureFactory.create(level = Level.BRONZE)
                val gameId1: UUID = UuidGenerator.create()
                val gameId2: UUID = UuidGenerator.create()
                val gameCards: List<GameCard> = listOf(
                    GameCardFixtureFactory.create(gameId = gameId1, price = 20.toBigDecimal()),
                    GameCardFixtureFactory.create(gameId = gameId1, price = 30.toBigDecimal()),
                    GameCardFixtureFactory.create(gameId = gameId2, price = 50.toBigDecimal()),
                )

                // act
                val updatedMember: Member = member.updateLevelWith(gameCards)

                // assert
                updatedMember.level shouldBe Level.GOLD
            }
        }

        context("골드 조건을 만족하지 못하면서 유효 카드가 1장 이상이면") {
            it("실버 레벨로 업데이트 한다.") {
                // arrange
                val member: Member = MemberFixtureFactory.create(level = Level.BRONZE)
                val gameCards: List<GameCard> = listOf(
                    GameCardFixtureFactory.create(price = 10.toBigDecimal()),
                )

                // act
                val updatedMember: Member = member.updateLevelWith(gameCards)

                // assert
                updatedMember.level shouldBe Level.SILVER
            }
        }

        context("유효 카드가 없으면") {
            it("브론즈 레벨로 업데이트 한다.") {
                // arrange
                val member: Member = MemberFixtureFactory.create(level = Level.GOLD)
                val gameCards: List<GameCard> = emptyList()

                // act
                val updatedMember: Member = member.updateLevelWith(gameCards)

                // assert
                updatedMember.level shouldBe Level.BRONZE
            }
        }
    }

})
