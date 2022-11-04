package ru.cinema.app.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCallPipeline.ApplicationPhase.Plugins
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import ru.cinema.api.chat.websocket.model.SessionData
import java.util.UUID

fun Application.configureSecurity() {
    install(Sessions) {
        cookie<SessionData>("SESSION")
    }

    intercept(Plugins) {
        if (call.sessions.get<SessionData>() == null) {
            call.sessions.set(SessionData(UUID.randomUUID()))
        }
    }
}
