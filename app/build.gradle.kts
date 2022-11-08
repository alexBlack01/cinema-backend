import com.bmuschko.gradle.docker.tasks.image.Dockerfile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.build.config.get().pluginId)
    id(libs.plugins.docker.java.get().pluginId)
    id(libs.plugins.kotlin.gradle.plugin.get().pluginId)
    id(libs.plugins.kotlin.serialization.get().pluginId)
    id(libs.plugins.cinema.build.get().pluginId)
    application
}

// region Docker & CI

val imageName: String = System.getenv("CI_REGISTRY_IMAGE") ?: "cinema-api"
val imageTag: String = System.getenv("IMAGE_TAG") ?: "dev"
val applicationHost: String = System.getenv("CI_ENVIRONMENT_URL") ?: "http://127.0.0.1"
val environment: String = System.getenv("BUILD_ENV") ?: "dev"

// endregion

buildConfig {
    buildConfigField(type = "boolean", name = "IS_DEV", value = "${environment == "dev"}")
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

docker {
    javaApplication {
        baseImage.set("openjdk:11-jre-slim")
        maintainer.set("AlexBlack")
        ports.set(listOf(8800))
        images.set(listOf("$imageName:$imageTag"))
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.bundles.ktor.server)
    implementation(libs.koin)
    implementation(libs.konform)
    implementation(libs.logback)
    implementation(libs.swagger)

    // Modules
    implementation(project(path = ":domain"))
    implementation(project(path = ":data"))
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Main-Class"] = "ru.cinema.app.ApplicationKt"
    }

    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}

tasks.named<Dockerfile>("dockerCreateDockerfile") {
    environmentVariable("APPLICATION_HOST", applicationHost)
}

tasks {
    create("stage").dependsOn("run")
}