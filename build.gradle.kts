plugins {
    kotlin("jvm") version "2.0.0"
    application
}

group = "jakare"
version = "0.0.1"

application {
    mainClass = "jakare.ApplicationKt"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:5.27.0.0"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-server-jetty")
    implementation("org.http4k:http4k-format-jackson")

    implementation("it.skrape:skrapeit:1.2.2")
    implementation("org.jetbrains.kotlinx:kotlinx-html:0.11.0")
    implementation("org.apache.solr:solr-solrj:9.6.1")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:2.0.0")
}
