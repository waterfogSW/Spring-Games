package com.splab.springgames.infrastructure.persistence.game.adapter

import com.splab.springgames.domain.game.domain.Game
import com.splab.springgames.infrastructure.persistence.PersistenceTestDescribeSpec
import com.splab.springgames.infrastructure.persistence.game.repository.GameJpaRepository
import io.kotest.core.annotation.DisplayName
import io.kotest.matchers.shouldBe
import java.util.*

@DisplayName("[Persistence] 게임 JPA 어댑터")
class GameJpaAdapterTest(
    private val sut: GameJpaAdapter,
    private val gameJpaRepository: GameJpaRepository,
) : PersistenceTestDescribeSpec({

    describe("findAll") {
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

    describe("getById") {
        it("id에 해당하는 게임을 조회한다") {
            // arrange
            // arranged by V5__insert_game.sql, 매직더게더링 ID
            val id: UUID = UUID.fromString("018df3a8-7c53-78d3-a076-c5b3f5851080")

            // act
            val result: Game = sut.getById(gameJpaRepository.findAll().first().id)

            // assert
            result.name.value shouldBe "매직더게더링"
        }
    }


})
