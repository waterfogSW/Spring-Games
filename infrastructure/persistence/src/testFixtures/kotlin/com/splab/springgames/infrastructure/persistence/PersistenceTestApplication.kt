package com.splab.springgames.infrastructure.persistence

import com.splab.springgames.infrastructure.persistence.common.config.PersistenceConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(PersistenceConfig::class)
class PersistenceTestApplication
