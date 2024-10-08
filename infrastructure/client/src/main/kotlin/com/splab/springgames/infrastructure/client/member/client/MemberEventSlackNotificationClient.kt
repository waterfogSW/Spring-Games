package com.splab.springgames.infrastructure.client.member.client

import com.splab.springgames.infrastructure.client.common.config.slack.SlackClientConfig
import com.splab.springgames.infrastructure.client.member.dto.SlackNotificationRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "member-event-slack-notification-client",
    url = "\${slack.webhook-url}",
    configuration = [SlackClientConfig::class]
)
interface MemberEventSlackNotificationClient {

    @PostMapping
    fun notifyLevelUpdated(
        @RequestBody
        request: SlackNotificationRequest
    )

}
