package com.splab.springgames.infrastructure.client

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.options
import io.kotest.core.spec.style.DescribeSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource


@ActiveProfiles("test", "client")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureWireMock(port = 0) // 0: random port
abstract class ClientTestDescribeSpec(body: DescribeSpec.() -> Unit = {}) : DescribeSpec(body) {

    companion object {

        private const val PORT = 8080
        const val SLACK_WEBHOOK_PATH = "/slack-webhook"
        val wireMockServer = WireMockServer(options().port(PORT))


        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("slack.webhook-url") {
                "http://localhost:$PORT$SLACK_WEBHOOK_PATH"
            }
        }

    }

}
