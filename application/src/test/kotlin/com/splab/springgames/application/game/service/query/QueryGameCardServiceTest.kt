package com.splab.springgames.application.game.service.query

import com.splab.springgames.application.game.port.inbound.QueryGameUseCase
import com.splab.springgames.application.member.port.outbound.GameCardRepositorySpy
import com.splab.springgames.application.member.service.query.QueryGameCardService
import com.splab.springgames.domain.game.GameFixtureFactory
import com.splab.springgames.domain.game.domain.Game
import com.splab.springgames.domain.member.GameCardFixtureFactory
import com.splab.springgames.support.common.uuid.UuidGenerator
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.*

@DisplayName("[Application] 게임 카드 조회")
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

            GameCardFixtureFactory.create(
                gameId = game.id,
                memberId = memberId
            ).also { gameCardRepository.save(it) }

            GameCardFixtureFactory.create(
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
