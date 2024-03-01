package com.splab.springgames.application.member.service

import com.splab.springgames.application.game.port.outbound.GameCardRepositorySpy
import com.splab.springgames.application.member.port.inbound.AddGameCardUseCase
import com.splab.springgames.application.member.port.outbound.MemberRepositorySpy
import com.splab.springgames.domain.member.MemberFixtureFactory
import com.splab.springgames.domain.member.domain.GameCard
import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.vo.GameCardPrice
import com.splab.springgames.domain.member.vo.GameCardSerialNumber
import com.splab.springgames.domain.member.vo.GameCardTitle
import com.splab.springgames.support.common.uuid.UuidGenerator
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.util.*

@DisplayName("게임 카드 추가 서비스 테스트")
class AddGameCardServiceTest : DescribeSpec({

    val gameCardRepository = GameCardRepositorySpy()
    val memberRepository = MemberRepositorySpy()
    val sut = AddGameCardService(
        gameCardRepository = gameCardRepository,
        memberRepository = memberRepository,
    )

    afterEach {
        gameCardRepository.clear()
        memberRepository.clear()
    }

    describe("게임 카드 추가") {
        context("성공 - 회원이 저장되어 있으면") {
            it("새로운 게임 카드를 저장하고 회원 정보를 업데이트 한다") {
                // arrange
                val member: Member = MemberFixtureFactory.create()
                val gameId: UUID = UuidGenerator.create()
                val title = "게임 카드 제목"
                val serialNumber = 1234L
                val price = 10.2.toBigDecimal()


                memberRepository.save(member)

                val command = AddGameCardUseCase.Command(
                    memberId = member.id,
                    gameId = gameId,
                    title = title,
                    serialNumber = serialNumber,
                    price = price,
                )

                // act
                sut.invoke(command)

                // assert
                val gameCard: GameCard = gameCardRepository.findAllByMemberId(member.id).first()
                gameCard.gameId shouldBe gameId
                gameCard.title shouldBe GameCardTitle(title)
                gameCard.serialNumber shouldBe GameCardSerialNumber(serialNumber)
                gameCard.price shouldBe GameCardPrice(price)

                val updatedMember: Member = memberRepository.getById(member.id)
                updatedMember.gameCardTotalCount.value shouldBe 1
                updatedMember.gameCardTotalPrice.value shouldBe price
            }
        }
    }


})
