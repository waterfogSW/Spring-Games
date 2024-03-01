package com.splab.springgames.application.game.service

import com.splab.springgames.application.member.port.inbound.AddGameCardUseCase
import com.splab.springgames.application.game.port.outbound.GameCardRepositorySpy
import com.splab.springgames.application.member.service.AddGameCardService
import com.splab.springgames.domain.member.domain.GameCard
import com.splab.springgames.support.common.uuid.UuidGenerator
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.math.BigDecimal
import java.util.*

@DisplayName("게임 카드 추가 서비스 테스트")
class AddGameCardServiceTest : DescribeSpec({

    val gameCardRepositorySpy = GameCardRepositorySpy()
    val sut = AddGameCardService(gameCardRepository = gameCardRepositorySpy)

    describe("게임 카드 추가") {
        it("새로운 게임 카드를 저장한다") {
            // arrange
            val gameId: UUID = UuidGenerator.create()
            val memberId: UUID = UuidGenerator.create()
            val title = "게임 제목"
            val serialNumber = 1234567890L
            val price = BigDecimal(10000)

            val command = AddGameCardUseCase.Command(
                gameId = gameId,
                memberId = memberId,
                title = title,
                serialNumber = serialNumber,
                price = price,
            )

            // act
            sut.invoke(command)

            // assert
            val savedGameCard: GameCard? = gameCardRepositorySpy.findByGameIdAndSerialNumber(
                gameId,
                serialNumber
            )
            savedGameCard shouldNotBe null
            savedGameCard?.gameId shouldBe gameId
            savedGameCard?.memberId shouldBe memberId
        }
    }


})
