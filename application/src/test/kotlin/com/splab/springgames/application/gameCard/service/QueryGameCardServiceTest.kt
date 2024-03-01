package com.splab.springgames.application.gameCard.service

import com.splab.springgames.application.game.port.inbound.QueryGameUseCase
import com.splab.springgames.application.gameCard.port.outbound.GameCardRepositorySpy
import com.splab.springgames.domain.game.GameFixtureFactory
import com.splab.springgames.domain.game.domain.Game
import com.splab.springgames.domain.gameCard.GameCardFixtureFactory
import com.splab.springgames.domain.gameCard.domain.GameCard
import com.splab.springgames.support.common.uuid.UuidGenerator
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.*

@DisplayName("게임 카드 조회 서비스 테스트")
class QueryGameCardServiceTest : DescribeSpec({

    val queryGameUseCase = mockk<QueryGameUseCase>()
    val gameCardRepository = GameCardRepositorySpy()
    val sut = QueryGameCardService(
        gameCardRepository = gameCardRepository,
        queryGameUseCase = queryGameUseCase,
    )

    describe("findAllInfosByMemberId") {
        it("회원의 모든 게임카드 정보를 조회한다.") {
            // arrange
            val memberId: UUID = UuidGenerator.create()
            val game: Game = GameFixtureFactory.create()

            every { queryGameUseCase.getById(game.id) } returns game

            val gameCard1: GameCard = GameCardFixtureFactory.create(
                gameId = game.id,
                memberId = memberId
            ).also { gameCardRepository.save(it) }

            val gameCard2: GameCard = GameCardFixtureFactory.create(
                gameId = game.id,
                memberId = memberId
            ).also { gameCardRepository.save(it) }

            // act
            val result = sut.findAllInfosByMemberId(memberId)

            // assert
            result.size shouldBe 2
        }
    }


})
