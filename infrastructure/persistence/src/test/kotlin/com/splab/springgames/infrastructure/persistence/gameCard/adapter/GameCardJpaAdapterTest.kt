package com.splab.springgames.infrastructure.persistence.gameCard.adapter

import com.splab.springgames.domain.gameCard.GameCardFixtureFactory
import com.splab.springgames.infrastructure.persistence.PersistenceTestDescribeSpec
import com.splab.springgames.infrastructure.persistence.gameCard.repository.GameCardJpaRepository
import io.kotest.core.annotation.DisplayName
import io.kotest.matchers.shouldNotBe
import kotlin.jvm.optionals.getOrNull

@DisplayName("게임 카드 JPA 어댑터 테스트")
class GameCardJpaAdapterTest(
    private val gameCardJpaRepository: GameCardJpaRepository,
    private val sut: GameCardJpaAdapter
) : PersistenceTestDescribeSpec({

    afterEach {
        gameCardJpaRepository.deleteAll()
    }

    describe("게임 카드 저장") {
        it("게임 카드를 저장한다") {
            // arrange
            val gameCard = GameCardFixtureFactory.create()

            // act
            sut.save(gameCard)

            // assert
            gameCardJpaRepository
                .findById(gameCard.id)
                .getOrNull() shouldNotBe null
        }
    }

})
