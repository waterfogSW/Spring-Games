package com.splab.springgames.infrastructure.persistence.game.adapter

import com.splab.springgames.domain.member.GameCardFixtureFactory
import com.splab.springgames.infrastructure.persistence.PersistenceTestDescribeSpec
import com.splab.springgames.infrastructure.persistence.game.entity.GameCardJpaEntity.Companion.toJpaEntity
import com.splab.springgames.infrastructure.persistence.game.repository.GameCardJpaRepository
import com.splab.springgames.support.common.uuid.UuidGenerator
import io.kotest.core.annotation.DisplayName
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.util.*
import kotlin.jvm.optionals.getOrNull

@DisplayName("[Persistence] 게임 카드 JPA 어댑터")
class GameCardJpaAdapterTest(
    private val gameCardJpaRepository: GameCardJpaRepository,
    private val sut: GameCardJpaAdapter
) : PersistenceTestDescribeSpec({

    afterEach {
        gameCardJpaRepository.deleteAll()
    }

    describe("save") {
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

    describe("existsByGameIdAndSerialNumber") {
        it("게임 아이디와 시리얼 번호로 게임 카드가 존재하는지 확인한다.") {
            // arrange
            val gameCard = GameCardFixtureFactory.create()
            gameCardJpaRepository.save(gameCard.toJpaEntity())

            // act
            val result = sut.existsByGameIdAndSerialNumber(gameCard.gameId, gameCard.serialNumber)

            // assert
            result shouldNotBe false
        }
    }

    describe("findById") {
        context("id값에 해당하는 게임 카드가 존재하는 경우") {
            it("게임 카드를 조회한다.") {
                // arrange
                val gameCard = GameCardFixtureFactory.create()
                gameCardJpaRepository.save(gameCard.toJpaEntity())

                // act
                val result = sut.findById(gameCard.id)

                // assert
                result shouldNotBe null
            }
        }

        context("id값에 해당하는 게임 카드가 존재하지 않는 경우") {
            it("null을 반환한다.") {
                // act
                val result = sut.findById(UuidGenerator.create())

                // assert
                result shouldBe null
            }
        }
    }

    describe("deleteById") {
        context("id값에 해당하는 게임 카드가 존재하는 경우") {
            it("게임 카드를 삭제한다.") {
                // arrange
                val gameCard = GameCardFixtureFactory.create()
                gameCardJpaRepository.save(gameCard.toJpaEntity())

                // act
                sut.deleteById(gameCard.id)

                // assert
                gameCardJpaRepository.findById(gameCard.id) shouldBe Optional.empty()
            }
        }

        context("id값에 해당하는 게임 카드가 존재하지 않는 경우") {
            it("아무런 동작을 하지 않는다.") {
                // act
                sut.deleteById(UuidGenerator.create())

                // assert
                gameCardJpaRepository.findAll() shouldBe emptyList()
            }
        }
    }

    describe("deleteAllByMemberId") {
        it("회원의 모든 게임 카드를 삭제한다.") {
            // arrange
            val memberId = UuidGenerator.create()
            val gameCard1 = GameCardFixtureFactory.create(memberId = memberId)
            val gameCard2 = GameCardFixtureFactory.create(memberId = memberId)

            gameCardJpaRepository.save(gameCard1.toJpaEntity())
            gameCardJpaRepository.save(gameCard2.toJpaEntity())

            // act
            sut.deleteAllByMemberId(memberId)

            // assert
            gameCardJpaRepository.findAll() shouldBe emptyList()
        }
    }

})
