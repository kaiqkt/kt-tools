plugins {
    kotlin("jvm") version "1.9.25"
    id("maven-publish")
}

allprojects {
    repositories {
        mavenCentral()
    }

    apply(plugin = "kotlin")
    apply(plugin = "maven-publish")

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web:3.4.3")
        testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
        testImplementation("io.mockk:mockk:1.13.14")
    }

    tasks.test {
        useJUnitPlatform()
    }
}
