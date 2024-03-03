package com.splab.springgames.infrastructure.client.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "slack")
data class SlackConfigurationProperties(
    val serverId: String,
)
