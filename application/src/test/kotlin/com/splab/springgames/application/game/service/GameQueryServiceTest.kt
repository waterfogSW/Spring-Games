package com.splab.springgames.application.game.service

import com.splab.springgames.application.game.port.outbound.GameRepositorySpy
import com.splab.springgames.domain.game.GameFixtureFactory
import com.splab.springgames.domain.game.entity.Game
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("게임 쿼리 서비스")
class GameQueryServiceTest : DescribeSpec({

    val gameRepositorySpy = GameRepositorySpy()
    val sut = GameQueryService(gameRepositorySpy)

    describe("목록 조회") {
        it("저장된 게임 목록을 조회한다") {
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
