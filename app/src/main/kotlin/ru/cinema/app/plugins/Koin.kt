package ru.cinema.app.plugins

import io.ktor.server.application.Application
import org.koin.ktor.plugin.koin
import ru.cinema.app.di.KoinModules

fun Application.configureKoin() {
    koin {
        modules(KoinModules.all)
    }
}
