package com.splab.springgames.infrastructure.persistence.member.adapter

import com.splab.springgames.domain.member.MemberFixtureFactory
import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.enum.Level
import com.splab.springgames.infrastructure.persistence.member.repository.MemberJpaRepository
import com.splab.springgames.support.common.exception.CustomException
import com.splab.springgames.support.common.uuid.UuidGenerator
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.jvm.optionals.getOrNull

@DisplayName("MemberJpaAdapter")
class MemberJpaAdapterTest(
    private val sut: MemberJpaAdapter,
    private val memberJpaRepository: MemberJpaRepository,
) : PersistenceTestDescribeSpec({

    afterTest {
        memberJpaRepository.deleteAll()
    }

    describe("save") {
        it("회원을 저장한다") {
            // arrange
            val member: Member = MemberFixtureFactory.create()

            // act
            sut.save(member)

            // assert
            val result: Member? = memberJpaRepository.findById(member.id).getOrNull()?.toDomain()
            result shouldNotBe null
            result!!.id shouldBe member.id
        }
    }

    describe("searchByFilter") {
        context("이름 검색시") {
            it(" 해당 이름을 포함하는 모든 회원을 반환한다") {
                // arrange
                MemberFixtureFactory
                    .create(name = "홍길동")
                    .also { sut.save(it) }

                MemberFixtureFactory
                    .create(name = "길동짱", email = "test124@naver.com")
                    .also { sut.save(it) }

                MemberFixtureFactory
                    .create(name = "홍길순", email = "test123@naver.com")
                    .also { sut.save(it) }

                // act
                val result: List<Member> = sut.searchByFilter(name = "길동")

                // assert
                result.size shouldBe 2
            }
        }

        context("레벨 검색시 ") {
            it("해당 레벨에 속하는 모든 회원을 반환한다") {
                // arrange
                MemberFixtureFactory
                    .create(level = Level.BRONZE)
                    .also { sut.save(it) }

                MemberFixtureFactory
                    .create(level = Level.SILVER, email = "test123@naver.com")
                    .also { sut.save(it) }

                // act
                val result: List<Member> = sut.searchByFilter(level = Level.BRONZE)

                // assert
                result.size shouldBe 1
            }
        }
    }

    describe("getById") {
        it("ID값에 해당하는 회원을 반환한다") {
            // arrange
            val member: Member = MemberFixtureFactory.create()
            sut.save(member)

            // act
            val result: Member = sut.getById(member.id)

            // assert
            result.id shouldBe member.id
        }

        context("ID값에 해당하는 회원이 없는 경우") {
            it("CustomException을 반환한다") {
                // act
                val result: Throwable = shouldThrow<CustomException> {
                    sut.getById(UuidGenerator.create())
                }

                // assert
                result.message shouldBe "회원을 찾을 수 없습니다."
            }
        }
    }

    describe("findByEmail") {
        it("이메일에 해당하는 회원을 반환한다") {
            // arrange
            val member: Member = MemberFixtureFactory.create()
            sut.save(member)

            // act
            val result: Member? = sut.findByEmail(member.email)

            // assert
            result shouldNotBe null
            result!!.id shouldBe member.id
        }
    }

    describe("deleteById") {
        it("ID값에 해당하는 회원을 삭제한다") {
            // arrange
            val member: Member = MemberFixtureFactory.create()
            sut.save(member)

            // act
            sut.deleteById(member.id)

            // assert
            val result: Member? = memberJpaRepository.findById(member.id).getOrNull()?.toDomain()
            result shouldBe null
        }
    }

})
