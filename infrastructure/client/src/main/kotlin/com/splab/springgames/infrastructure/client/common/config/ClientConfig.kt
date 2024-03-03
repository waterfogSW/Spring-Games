package com.splab.springgames.infrastructure.client.common.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["com.splab.springgames.infrastructure.client"])
@EnableFeignClients(basePackages = ["com.splab.springgames.infrastructure.client"])
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = ["com.splab.springgames.infrastructure.client"])
class ClientConfig
