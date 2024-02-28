package com.splab.springgames.infrastructure.persistence.ui

import com.splab.springgames.application.member.common.config.ApplicationConfig
import com.splab.springgames.infrastructure.persistence.common.config.PersistenceConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(
    value = [
        ApplicationConfig::class,
        PersistenceConfig::class,
    ]
)
class SpringGamesUiApplication

fun main(args: Array<String>) {
    runApplication<SpringGamesUiApplication>(*args)
}
