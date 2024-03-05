package com.splab.springgames.infrastructure.client.member.adapter

import com.splab.springgames.application.member.port.outbound.MemberEventNotifier
import com.splab.springgames.domain.member.domain.Member
import com.splab.springgames.infrastructure.client.common.properties.SlackConfigurationProperties
import com.splab.springgames.infrastructure.client.member.client.MemberEventSlackNotificationClient
import com.splab.springgames.infrastructure.client.member.dto.SlackNotificationRequest
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class MemberEventOpenFeignAdapter(
    private val memberEventSlackNotificationClient: MemberEventSlackNotificationClient,
    private val slackConfigurationProperties: SlackConfigurationProperties,
) : MemberEventNotifier {

    override fun notifyLevelUpdated(member: Member): CompletableFuture<Unit> {
        val message = buildMessage(member)
        val request = SlackNotificationRequest(message)
        memberEventSlackNotificationClient.notifyLevelUpdated(request)
        return CompletableFuture.completedFuture(Unit)
    }

    private fun buildMessage(currentMember: Member): String {
        return """
            지원자명 : ${slackConfigurationProperties.serverId}
            회원 ID : ${currentMember.id}
            회원명 : ${currentMember.name.value}
            새롭게 부여된 레벨 : ${currentMember.level}
            """.trimIndent()
    }

}
