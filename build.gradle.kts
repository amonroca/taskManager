plugins {
    kotlin("jvm") version "2.0.0"
    application
}

group = "com.taskmanager"
version = "1.0.0"

repositories {
    mavenCentral()
}

application {
    mainClass.set("taskmanager.MainKt")
}

// Wire stdin through so 'gradlew run' works interactively
tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

kotlin {
    jvmToolchain(20)
}
