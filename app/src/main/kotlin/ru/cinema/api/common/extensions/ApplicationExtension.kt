package ru.cinema.api.common.extensions

import io.ktor.server.application.Application
import ru.cinema.app.common.utils.SystemEnvVariablesUtil

fun Application.getBaseUrl(): String {
    val rootPath = environment.rootPath
    val host = SystemEnvVariablesUtil.host
    return "$host$rootPath"
}
