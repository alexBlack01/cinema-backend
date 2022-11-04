package ru.cinema.app.plugins

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import ru.cinema.api.common.serializations.LocalDateTimeSerializer
import ru.cinema.api.common.serializations.UUIDSerializer
import java.time.LocalDateTime
import java.util.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(
            json = Json {
                coerceInputValues = true
                ignoreUnknownKeys = true
                prettyPrint = true
                serializersModule = SerializersModule {
                    contextual(UUID::class, UUIDSerializer)
                    contextual(LocalDateTime::class, LocalDateTimeSerializer())
                }
            }
        )
    }
}
