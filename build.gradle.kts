plugins {
    id("java")
}

group = "com.example"
version = "1.0-SNAPSHOT"
val lombokVersion = "1.18.32"
val slf4jVersion = "2.0.13"
val reactorVersion = "3.5.15"
val springbootVersion = "3.5.5"
val logbackVersion = "1.5.13"
val junitBomVersion = "5.9.3"
val mockitoVersion = "5.1.1"
val reactorTestVersion = "3.1.0.RELEASE"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation ("io.projectreactor:reactor-core:$reactorVersion")
    runtimeOnly("ch.qos.logback:logback-classic:$logbackVersion")
    // Testing
    testImplementation(platform("org.junit:junit-bom:$junitBomVersion"))
    testImplementation("io.projectreactor:reactor-test:$reactorTestVersion")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("org.mockito:mockito-junit-jupiter:$mockitoVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}