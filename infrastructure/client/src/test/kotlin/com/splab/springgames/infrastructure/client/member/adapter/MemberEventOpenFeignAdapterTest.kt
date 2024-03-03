package com.splab.springgames.infrastructure.client.member.adapter

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import com.splab.springgames.domain.member.MemberFixtureFactory
import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.infrastructure.client.ClientTestDescribeSpec
import com.splab.springgames.infrastructure.client.common.properties.SlackConfigurationProperties
import wiremock.org.eclipse.jetty.http.HttpStatus

class MemberEventOpenFeignAdapterTest(
    private val sut: MemberEventOpenFeignAdapter,
    private val properties: SlackConfigurationProperties
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
        it("레벨 변경 알림을 보낸다") {
            // arrange
            val member: Member = MemberFixtureFactory.create()
            wireMockServer.stubFor(
                post(urlMatching(SLACK_WEBHOOK_PATH))
                    .willReturn(aResponse().withStatus(HttpStatus.OK_200))
            )

            // act
            sut.notifyLevelUpdated(member)

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


})
