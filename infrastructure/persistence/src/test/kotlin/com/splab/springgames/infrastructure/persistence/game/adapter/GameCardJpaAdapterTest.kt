package com.splab.springgames.infrastructure.persistence.game.adapter

import com.splab.springgames.domain.member.GameCardFixtureFactory
import com.splab.springgames.infrastructure.persistence.PersistenceTestDescribeSpec
import com.splab.springgames.infrastructure.persistence.game.entity.GameCardJpaEntity.Companion.toJpaEntity
import com.splab.springgames.infrastructure.persistence.game.repository.GameCardJpaRepository
import com.splab.springgames.support.common.uuid.UuidGenerator
import io.kotest.core.annotation.DisplayName
import io.kotest.matchers.shouldNotBe
import kotlin.jvm.optionals.getOrNull

@DisplayName("GameCardJpaAdapter")
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

    describe("findAllByMemberId") {
        it("회원의 모든 게임카드를 조회한다.") {
            // arrange
            val memberId = UuidGenerator.create()
            val gameCard = GameCardFixtureFactory.create(memberId = memberId)
            gameCardJpaRepository.save(gameCard.toJpaEntity())

            // act
            val result = sut.findAllByMemberId(memberId)

            // assert
            result.size shouldNotBe 0
        }
    }

})
