package com.splab.springgames.infrastructure.client

import com.splab.springgames.infrastructure.client.common.config.ClientConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(ClientConfig::class)
class ClientTestApplication
