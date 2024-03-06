package com.splab.springgames.infrastructure.client.common.config.slack

import feign.Retryer
import org.springframework.context.annotation.Bean

class SlackClientConfig {

    @Bean
    fun retryer(): Retryer.Default {
        return Retryer.Default(100, 1000, 3)
    }

    @Bean
    fun errorDecoder(): SlackErrorDecoder {
        return SlackErrorDecoder()
    }

}
