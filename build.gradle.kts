plugins {
    kotlin("jvm") version "1.9.20"
}

repositories {
    mavenCentral()
}

val kotestVersion = "5.8.0"

dependencies {
    implementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
    implementation(libs.kotest.runner.junit5)
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "8.4"
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
