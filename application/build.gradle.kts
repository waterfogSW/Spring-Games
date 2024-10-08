import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

dependencies {
    implementation(project(":support:common"))
    implementation(project(":domain"))

    implementation("org.springframework:spring-tx:${Version.SPRING}")
    implementation("org.springframework.boot:spring-boot:${Version.SPRING_BOOT}")
    testImplementation(testFixtures(project(":domain")))
}
