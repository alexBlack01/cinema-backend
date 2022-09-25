package ru.cinema.plugin

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra
import ru.cinema.plugin.common.Constants
import ru.cinema.plugin.common.SortedProperties
import java.io.File
import java.io.FileInputStream

class CinemaBuildPlugin : Plugin<Project> {
    private lateinit var versionPropsFile: File
    private lateinit var versionProps: SortedProperties

    override fun apply(target: Project) {
        readProperties(target)
    }

    private fun readProperties(target: Project) {
        println("Reading version properties for project...")
        versionPropsFile = File("${target.projectDir.absolutePath}/version.properties")
        if (versionPropsFile.canRead()) {
            versionProps = SortedProperties().also {
                it.load(FileInputStream(versionPropsFile))
            }
            println("Properties are: $versionProps")
            val version = versionProps
                .getOrDefault(Constants.CONFIG_VERSION_NAME_PROPERTY, "0.0.0")
                .toString()

            target.extra[Constants.EXTRA_VERSION_NAME] = version
        } else {
            throw GradleException("Could not read version.properties!")
        }
    }
}