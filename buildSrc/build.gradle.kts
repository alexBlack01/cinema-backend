@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    libs.plugins.kotlin.dsl.get().apply {
        id(pluginId) version (version.requiredVersion)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.detekt.gradle)
    implementation(gradleApi())
    implementation(localGroovy())
}