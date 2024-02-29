package com.splab.springgames.infrastructure.persistence.game.adapter

import com.splab.springgames.domain.game.entity.Game
import com.splab.springgames.infrastructure.persistence.PersistenceTestDescribeSpec
import com.splab.springgames.infrastructure.persistence.game.repository.GameJpaRepository
import io.kotest.core.annotation.DisplayName
import io.kotest.matchers.shouldBe

@DisplayName("GameJpaAdapterTest")
class GameJpaAdapterTest(
    private val sut: GameJpaAdapter,
    private val gameJpaRepository: GameJpaRepository,
) : PersistenceTestDescribeSpec({

    afterTest {
        gameJpaRepository.deleteAll()
    }

    describe("전체 게임 목록 조회") {
        it("저장된 모든 게임 목록을 조회한다") {
            // arrange
            // arranged by V5__insert_game.sql

            // act
            val result: List<Game> = sut.findAll()

            // assert
            result.size shouldBe 3

            result[0].name.value shouldBe "매직더게더링"
            result[1].name.value shouldBe "유희왕"
            result[2].name.value shouldBe "포켓몬"
        }
    }


})
