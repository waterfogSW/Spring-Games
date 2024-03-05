package com.splab.springgames.application.common.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync

@Configuration
@ComponentScan(basePackages = ["com.splab.springgames.application"], lazyInit = true)
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = ["com.splab.springgames.application"])
@EnableAsync
class ApplicationConfig
