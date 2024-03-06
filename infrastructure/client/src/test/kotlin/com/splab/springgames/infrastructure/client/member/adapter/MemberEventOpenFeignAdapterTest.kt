package com.splab.springgames.infrastructure.client.member.adapter

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import com.github.tomakehurst.wiremock.client.WireMock.verify
import com.splab.springgames.application.member.port.outbound.MemberEventNotifier
import com.splab.springgames.domain.member.MemberFixtureFactory
import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.infrastructure.client.ClientTestDescribeSpec
import com.splab.springgames.infrastructure.client.common.properties.SlackConfigurationProperties
import com.splab.springgames.support.common.exception.CustomException
import feign.FeignException
import io.kotest.assertions.fail
import io.kotest.core.annotation.DisplayName
import io.kotest.matchers.shouldBe
import wiremock.org.eclipse.jetty.http.HttpStatus

@DisplayName("[Client] 회원 이벤트 알림 어댑터")
class MemberEventOpenFeignAdapterTest(
    private val sut: MemberEventNotifier,
    private val properties: SlackConfigurationProperties,
) : ClientTestDescribeSpec({

    beforeTest {
        wireMockServer.start()
    }

    afterTest {
        wireMockServer.stop()
    }

    afterEach {
        wireMockServer.resetAll()
    }

    describe("notifyLevelUpdated") {
        context("[성공] 알림 전송에 성공하면") {

            it("레벨 변경 알림을 보낸다") {
                // arrange
                val member: Member = MemberFixtureFactory.create()
                wireMockServer.stubFor(
                    post(urlMatching(SLACK_WEBHOOK_PATH))
                        .willReturn(aResponse().withStatus(HttpStatus.OK_200))
                )

                // act
                sut.notifyLevelUpdated(member).get()

                // assert
                val notificationText =
                    """
                지원자명 : ${properties.serverId}
                회원 ID : ${member.id}
                회원명 : ${member.name.value}
                새롭게 부여된 레벨 : ${member.level}
                """.trimIndent()

                wireMockServer.verify(
                    postRequestedFor(urlMatching(SLACK_WEBHOOK_PATH))
                        .withRequestBody(matchingJsonPath("$.text", equalTo(notificationText)))
                )
            }
        }

        context("[실패] 400 에러를 응답받으면") {
            it("CustomException(code = Client-001)예외를 던진다") {
                // arrange
                val member: Member = MemberFixtureFactory.create()
                wireMockServer.stubFor(
                    post(urlMatching(SLACK_WEBHOOK_PATH))
                        .willReturn(aResponse().withStatus(HttpStatus.BAD_REQUEST_400))
                )

                // act
                try {
                    sut.notifyLevelUpdated(member).get()
                    fail("CustomException(code = Client-001)예외가 발생해야 합니다.")
                } catch (e: CustomException) {
                    e.type.code shouldBe "CLIENT-001"
                }
            }
        }

        context("[실패] 403 에러를 응답받으면") {
            it("CustomException(code = Client-001)예외를 던진다") {
                // arrange
                val member: Member = MemberFixtureFactory.create()
                wireMockServer.stubFor(
                    post(urlMatching(SLACK_WEBHOOK_PATH))
                        .willReturn(aResponse().withStatus(HttpStatus.FORBIDDEN_403))
                )

                // act
                try {
                    sut.notifyLevelUpdated(member).get()
                    fail("CustomException(code = Client-001)예외가 발생해야 합니다.")
                } catch (e: CustomException) {
                    e.type.code shouldBe "CLIENT-001"
                }
            }
        }

        context("[실패] 404 에러를 응답받으면") {
            it("CustomException(code = Client-001)예외를 던진다") {
                // arrange
                val member: Member = MemberFixtureFactory.create()
                wireMockServer.stubFor(
                    post(urlMatching(SLACK_WEBHOOK_PATH))
                        .willReturn(aResponse().withStatus(HttpStatus.NOT_FOUND_404))
                )

                // act
                try {
                    sut.notifyLevelUpdated(member).get()
                    fail("CustomException(code = Client-001)예외가 발생해야 합니다.")
                } catch (e: CustomException) {
                    e.type.code shouldBe "CLIENT-001"
                }
            }
        }

        context("[실패] 410 Gone 에러를 응답받으면") {
            it("CustomException(code = Client-001)예외를 던진다") {
                // arrange
                val member: Member = MemberFixtureFactory.create()
                wireMockServer.stubFor(
                    post(urlMatching(SLACK_WEBHOOK_PATH))
                        .willReturn(aResponse().withStatus(HttpStatus.GONE_410))
                )

                // act
                try {
                    sut.notifyLevelUpdated(member).get()
                    fail("CustomException(code = Client-001)예외가 발생해야 합니다.")
                } catch (e: CustomException) {
                    e.type.code shouldBe "CLIENT-001"
                }
            }
        }

        context("[실패] 500 Internal Server Error를 응답받으면") {
            it("3번 재시도후 Internal Server Error 예외를 던진다") {
                // arrange
                val member: Member = MemberFixtureFactory.create()
                wireMockServer.stubFor(
                    post(urlMatching(SLACK_WEBHOOK_PATH))
                        .willReturn(aResponse().withStatus(HttpStatus.INTERNAL_SERVER_ERROR_500))
                )

                // act & assert
                try {
                    sut.notifyLevelUpdated(member).get()
                    fail("Internal Server Error 예외가 발생해야 합니다.")
                } catch (e: FeignException) {
                    e.status() shouldBe HttpStatus.INTERNAL_SERVER_ERROR_500
                } finally {
                    verify(3, postRequestedFor(urlMatching(SLACK_WEBHOOK_PATH)))
                }
            }
        }
    }

})
