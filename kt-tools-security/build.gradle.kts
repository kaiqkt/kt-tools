plugins {
    kotlin("jvm") version "2.1.10"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security:3.4.3")
    implementation("com.auth0:java-jwt:4.4.0")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/kaiqkt/springtools")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GPR_USER")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GPR_API_KEY")
            }
        }
    }

    publications {
        create<MavenPublication>("security") {
            from(components["kotlin"])
            groupId = "com.kaiqkt"
            artifactId = "kt-tools-security"
            version = "1.0.0"
        }
    }
}
