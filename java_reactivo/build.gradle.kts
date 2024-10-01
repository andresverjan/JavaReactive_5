plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}


dependencies {
    implementation("io.projectreactor:reactor-core:3.5.0")
    implementation ("io.reactivex.rxjava2:rxjava:2.2.20")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.projectreactor:reactor-test:3.5.0")
}

tasks.test {
    useJUnitPlatform()
}