package ru.cinema.app.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import ru.cinema.websocket.WebSocketConstants
import java.time.Duration

fun Application.configureWebSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(WebSocketConstants.PING_PERIOD)
        timeout = Duration.ofSeconds(WebSocketConstants.TIMEOUT)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
}
