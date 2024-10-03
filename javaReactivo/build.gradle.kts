plugins {
    id("java")
}

group = "com.lab"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    implementation("io.projectreactor:reactor-core:3.5.0")
    implementation ("io.reactivex.rxjava2:rxjava:2.2.20")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.projectreactor:reactor-test:3.5.0")
}

tasks.test {
    useJUnitPlatform()
}