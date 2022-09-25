package ru.cinema.app.plugins

import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.Application
import org.koin.ktor.ext.inject
import ru.cinema.data.db.common.Database

fun Application.configureFlyway() {
    val hikariDataSource by inject<HikariDataSource>()
    Database.runMigrations(hikariDataSource)
}
