plugins {
    java
    application
}

group = "com.monpoke"
version = "1.0-SNAPSHOT"
description = "MonpokeGame"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:4.4.0")
    testImplementation("com.github.stefanbirkner:system-rules:1.19.0")
}

application {
    mainClass.set("com.monpoke.GameRunner")
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.named("run", JavaExec::class) {
    standardInput = System.`in`
}

tasks.create("runSample", JavaExec::class) {
    args = listOf("sample_input.txt")
    mainClass.set("com.monpoke.GameRunner")
    classpath = sourceSets["main"].runtimeClasspath
}

