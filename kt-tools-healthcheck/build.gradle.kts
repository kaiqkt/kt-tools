plugins {
    kotlin("jvm") version "1.9.25"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/kaiqkt/kt-tools")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GPR_USER")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GPR_API_KEY")
            }
        }
    }

    publications {
        create<MavenPublication>("healthcheck") {
            from(components["kotlin"])
            groupId = "com.kaiqkt"
            artifactId = "kt-tools-healthcheck"
            version = "1.0.0"
        }
    }
}
