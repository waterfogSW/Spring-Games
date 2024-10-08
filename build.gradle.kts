import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val springCloudVersion by extra("2023.0.0")

plugins {
    id("java-test-fixtures")
    id("org.springframework.boot") version Version.SPRING_BOOT
    id("io.spring.dependency-management") version Version.SPRING_BOOT_DEPENDENCY_MANAGEMENT
    kotlin("jvm") version Version.KOTLIN
    kotlin("plugin.jpa") version Version.KOTLIN
    kotlin("plugin.spring") version Version.KOTLIN
}

allprojects {
    group = "com.splab"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java-test-fixtures")
    apply(plugin = "idea")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")
    apply(plugin = "kotlin-jpa")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "kotlin-noarg")
    apply(plugin = "kotlin-allopen")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    dependencies {
        implementation("io.github.oshai:kotlin-logging-jvm:${Version.KOTLIN_LOGGING}")
        implementation("org.jetbrains.kotlin:kotlin-stdlib:${Version.KOTLIN}")
        implementation("org.jetbrains.kotlin:kotlin-reflect:${Version.KOTLIN}")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Version.JACKSON}")
        implementation("com.fasterxml.jackson.core:jackson-databind:${Version.JACKSON}")

        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:${Version.SPRING_BOOT}")

        testImplementation("io.mockk:mockk:${Version.MOCKK}")
        testImplementation("com.ninja-squad:springmockk:${Version.SPRING_MOCKK}")
        testImplementation("io.kotest:kotest-runner-junit5:${Version.KOTEST}")
        testImplementation("io.kotest:kotest-assertions-core:${Version.KOTEST}")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:${Version.KOTEST_EXTENSIONS_SPRING}")
        testImplementation("org.springframework.boot:spring-boot-starter-test:${Version.SPRING_BOOT}")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:${Version.KOTEST_EXTENSIONS_SPRING}")
        testImplementation("io.kotest.extensions:kotest-extensions-testcontainers:${Version.KOTEST_EXTENSIONS_TESTCONTAINERS}")
        testImplementation("org.testcontainers:junit-jupiter:${Version.TESTCONTAINERS}")

        testRuntimeOnly("com.h2database:h2:${Version.H2}")
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(Version.JAVA))
        }
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = Version.JAVA
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
        }
    }
}
