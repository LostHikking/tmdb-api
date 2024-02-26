val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.22"
    id("io.ktor.plugin") version "2.3.8"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
    id("org.jetbrains.kotlinx.kover") version "0.7.6"
}

group = "io.github.hikking"
version = "0.0.1"

application {
    mainClass.set("io.github.hikking.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-client-core")
    implementation("io.ktor:ktor-client-auth")
    implementation("io.ktor:ktor-client-okhttp")
    implementation("io.ktor:ktor-client-logging")
    implementation("io.ktor:ktor-client-resources")
    implementation("io.ktor:ktor-client-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("io.kotest:kotest-framework-datatest:5.8.0")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
