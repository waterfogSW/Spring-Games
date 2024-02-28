package com.splab.springgames.infrastructure.persistence.common.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@ComponentScan(basePackages = ["com.splab.springgames.infrastructure.persistence"])
@EntityScan(basePackages = ["com.splab.springgames.infrastructure.persistence"])
@EnableJpaRepositories(basePackages = ["com.splab.springgames.infrastructure.persistence"])
class PersistenceConfig
