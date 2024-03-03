import org.springframework.boot.gradle.tasks.bundling.BootJar

val springCloudVersion by extra("2023.0.0")
val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

dependencies {
    implementation(project(":support:common"))
    implementation(project(":domain"))
    implementation(project(":application"))

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
    testImplementation(testFixtures(project(":domain")))
}
