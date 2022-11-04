package ru.cinema.app

import io.ktor.server.application.Application
import ru.cinema.app.plugins.configureAuth
import ru.cinema.app.plugins.configureFlyway
import ru.cinema.app.plugins.configureHTTP
import ru.cinema.app.plugins.configureKoin
import ru.cinema.app.plugins.configureMonitoring
import ru.cinema.app.plugins.configureRouting
import ru.cinema.app.plugins.configureScheduledJobs
import ru.cinema.app.plugins.configureSecurity
import ru.cinema.app.plugins.configureSerialization
import ru.cinema.app.plugins.configureStatic
import ru.cinema.app.plugins.configureStatusPages
import ru.cinema.app.plugins.configureSwagger
import ru.cinema.app.plugins.configureWebSockets

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function.
fun Application.appModule() {
    configureMonitoring()
    configureKoin()
    configureHTTP()
    configureWebSockets()
    configureSecurity()
    configureRouting()
    configureFlyway()
    configureStatic()
    configureSwagger()
    configureSerialization()
    configureStatusPages()
    configureAuth()
    configureScheduledJobs()
}
