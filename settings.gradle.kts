rootProject.name = "advent-of-code-2023"

val kotestVersion = "5.8.0"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("kotest-runner-junit5", "io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
        }
    }
}