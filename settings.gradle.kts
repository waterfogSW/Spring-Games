plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "Spring-Games"

// support
include(":support:common")
project(":support:common").projectDir = file("support/common")

// domain
include(":domain")
project(":domain").projectDir = file("domain")

// application
include(":application")
project(":application").projectDir = file("application")

// infrastructure
include(":infrastructure:persistence")
project(":infrastructure:persistence").projectDir = file("infrastructure/persistence")

// bootstrap
include(":bootstrap:ui")
project(":bootstrap:ui").projectDir = file("bootstrap/ui")
