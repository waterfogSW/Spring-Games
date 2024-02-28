package com.splab.springgames.infrastructure.persistence.member.adapter

import com.splab.springgames.infrastructure.persistence.PersistenceTestApplication
import io.kotest.core.spec.style.DescribeSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.ComposeContainer
import java.io.File

@SpringBootTest
@Import(PersistenceTestApplication::class)
@ActiveProfiles("test")
abstract class PersistenceTestDescribeSpec(body: DescribeSpec.() -> Unit = {}) :
    DescribeSpec(body) {

    companion object {

        private val container: ComposeContainer =
            ComposeContainer(File("src/test/resources/compose-test.yaml"))
                .withExposedService("mysql", 3306)

        init {
            container.start()
        }

    }

}
