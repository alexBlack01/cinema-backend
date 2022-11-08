import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.detekt.get().pluginId)
}

buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.kotlin.serialization)
        classpath(libs.flyway.pluging)
        classpath(libs.build.config.pluging)
        classpath(libs.docker.java.plugin)
    }
}

allprojects {
    repositories {
        mavenCentral()
        maven(url = "https://jitpack.io")
    }

    group = "ru.cinema"

    tasks.withType<JavaCompile> {
        sourceCompatibility = libs.versions.java.asProvider().get()
        targetCompatibility = libs.versions.java.asProvider().get()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = libs.versions.java.asProvider().get()
            languageVersion = libs.versions.kotlin.lang.get()
        }
    }
}

subprojects {
    apply {
        plugin(rootProject.libs.plugins.detekt.get().pluginId)
    }

    detekt {
        autoCorrect = true
        config = files("$rootDir/config/detekt/detekt.yml")
    }

    dependencies {
        detektPlugins(rootProject.libs.detekt.formatting)
    }
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = libs.versions.java.asProvider().get()
}

tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = libs.versions.java.asProvider().get()
}

tasks {
    create("stage").dependsOn("build")
}