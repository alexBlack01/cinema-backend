package ru.cinema.app.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin
import ru.cinema.app.di.KoinModules.appModules

fun Application.configureKoin() {
    install(Koin) {
        modules(appModules)
    }
}
