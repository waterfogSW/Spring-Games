package com.splab.springgames.application.member.service.command

import com.splab.springgames.application.game.port.outbound.GameCardRepositorySpy
import com.splab.springgames.application.member.port.outbound.MemberEventNotifierSpy
import com.splab.springgames.application.member.port.outbound.MemberRepositorySpy
import com.splab.springgames.domain.member.GameCardFixtureFactory
import com.splab.springgames.domain.member.MemberFixtureFactory
import com.splab.springgames.domain.member.domain.GameCard
import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.enum.Level
import com.splab.springgames.domain.member.vo.GameCardTotalCount
import com.splab.springgames.domain.member.vo.GameCardTotalPrice
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("[Application] 게임 카드 삭제")
class DeleteGameCardServiceTest : DescribeSpec({

    val memberRepositorySpy = MemberRepositorySpy()
    val gameCardRepositorySpy = GameCardRepositorySpy()
    val memberEventNotifier = MemberEventNotifierSpy()
    val sut = DeleteGameCardService(
        gameCardRepository = gameCardRepositorySpy,
        memberRepository = memberRepositorySpy,
        memberEventNotifier = memberEventNotifier
    )

    afterEach {
        memberRepositorySpy.clear()
        gameCardRepositorySpy.clear()
        memberEventNotifier.clear()
    }

    describe("게임 카드 삭제") {
        context("[성공] ID값에 해당하는 게임 카드가 존재하는 경우") {
            it("게임 카드를 삭제하고, 회원 정보를 업데이트 한다") {
                // arrange
                val existsGameCardCount = GameCardTotalCount(10)
                val existsGameCardTotalPrice = GameCardTotalPrice(1000.toBigDecimal())

                val gameCardPrice = 100.toBigDecimal()

                val member: Member = MemberFixtureFactory.create(
                    gameCardTotalCount = existsGameCardCount,
                    gameCardTotalPrice = existsGameCardTotalPrice,
                    level = Level.SILVER,
                )
                val gameCard: GameCard = GameCardFixtureFactory.create(
                    memberId = member.id,
                    price = gameCardPrice
                )

                gameCardRepositorySpy.save(gameCard)
                memberRepositorySpy.save(member)

                // act
                sut.invoke(gameCard.id)

                // assert
                gameCardRepositorySpy.findById(gameCard.id) shouldBe null
                val updatedMember: Member = memberRepositorySpy.getById(member.id)

                updatedMember.gameCardTotalCount shouldBe GameCardTotalCount(existsGameCardCount.value - 1)
                updatedMember.gameCardTotalPrice shouldBe GameCardTotalPrice(
                    existsGameCardTotalPrice.value - gameCardPrice
                )
                updatedMember.level shouldBe Level.BRONZE
            }
        }

        context("[성공] 게임 카드 삭제로 인해 레벨이 변경된 경우") {
            it("레벨 변경 알림을 보낸다.") {
                // arrange
                val existsGameCardCount = GameCardTotalCount(1)
                val existsGameCardTotalPrice = GameCardTotalPrice(100.toBigDecimal())

                val member: Member = MemberFixtureFactory.create(
                    gameCardTotalCount = existsGameCardCount,
                    gameCardTotalPrice = existsGameCardTotalPrice,
                    level = Level.SILVER,
                )
                val gameCard: GameCard = GameCardFixtureFactory.create(
                    memberId = member.id,
                    price = 100.toBigDecimal()
                )

                gameCardRepositorySpy.save(gameCard)
                memberRepositorySpy.save(member)

                // act
                sut.invoke(gameCard.id)

                // assert
                memberEventNotifier.count() shouldBe 1
            }
        }

        context("[성공] ID값에 해당하는 게임 카드가 존재하지 않는 경우") {
            it("아무런 동작을 하지 않는다") {
                // act & assert
                sut.invoke(GameCardFixtureFactory.create().id)
            }
        }
    }

})
