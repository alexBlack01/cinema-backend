@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.kotlin.gradle.plugin.get().pluginId)
    id(libs.plugins.kotlin.serialization.get().pluginId)
    id(libs.plugins.flyway.get().pluginId)
}

dependencies {
    implementation(libs.kotlin.stdlib)
    api(libs.uploadcare.client)
    api(libs.kotlinx.serialization.json)

    api(libs.hikari)
    api(libs.exposed.core)
    implementation(libs.bundles.exposed)
    implementation(libs.postgree.connector)
    implementation(libs.flyway.core)

    implementation(libs.guava)
    implementation(libs.email)

    // Modules
    implementation(project(path = ":domain"))
}