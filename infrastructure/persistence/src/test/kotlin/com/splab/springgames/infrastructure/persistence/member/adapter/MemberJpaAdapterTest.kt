package com.splab.springgames.infrastructure.persistence.member.adapter

import com.splab.springgames.domain.member.MemberFixtureFactory
import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.domain.member.enum.Level
import com.splab.springgames.domain.member.vo.Name
import com.splab.springgames.infrastructure.persistence.member.repository.MemberJpaRepository
import io.kotest.core.annotation.DisplayName
import io.kotest.matchers.shouldBe

@DisplayName("MemberJpaAdapter")
class MemberJpaAdapterTest(
    private val sut: MemberJpaAdapter,
    private val memberJpaRepository: MemberJpaRepository,
) : PersistenceTestDescribeSpec({

    afterTest {
        memberJpaRepository.deleteAll()
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

})
