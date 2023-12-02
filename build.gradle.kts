plugins {
    kotlin("jvm") version "1.9.20"
}

kotlin {
    jvmToolchain(20)
}


repositories {
    mavenCentral()
}

val kotestVersion = "5.8.0"

dependencies {
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
    testImplementation(libs.kotest.runner.junit5)
}

tasks {
    wrapper {
        gradleVersion = "8.4"
    }
}

tasks.test {
    useJUnitPlatform()
}

