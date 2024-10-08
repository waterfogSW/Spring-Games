dependencies {
    implementation(project(":support:common"))
    implementation(project(":domain"))
    implementation(project(":application"))
    implementation(project(":infrastructure:persistence"))
    implementation(project(":infrastructure:client"))

    implementation("org.springframework.boot:spring-boot-starter-web:${Version.SPRING_BOOT}")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:${Version.SPRING_BOOT}")

    developmentOnly("org.springframework.boot:spring-boot-devtools:${Version.SPRING_BOOT}")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose:${Version.SPRING_BOOT}")
}
