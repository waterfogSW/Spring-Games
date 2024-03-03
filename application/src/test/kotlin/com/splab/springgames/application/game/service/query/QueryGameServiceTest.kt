package com.splab.springgames.application.game.service.query

import com.splab.springgames.application.game.port.outbound.GameRepositorySpy
import com.splab.springgames.application.game.service.QueryGameService
import com.splab.springgames.domain.game.GameFixtureFactory
import com.splab.springgames.domain.game.domain.Game
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("[Application] 게임 조회")
class QueryGameServiceTest : DescribeSpec({

    val gameRepositorySpy = GameRepositorySpy()
    val sut = QueryGameService(gameRepositorySpy)

    describe("목록 조회") {
        it("[성공] 저장된 게임 목록을 조회한다") {
            // arrange
            GameFixtureFactory
                .create(name = "게임1")
                .also { gameRepositorySpy.save(it) }

            GameFixtureFactory
                .create(name = "게임2")
                .also { gameRepositorySpy.save(it) }

            // act
            val result: List<Game> = sut.findAll()

            // assert
            result.size shouldBe 2
        }
    }


})
