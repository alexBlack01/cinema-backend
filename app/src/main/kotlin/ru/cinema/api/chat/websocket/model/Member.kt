package ru.cinema.api.chat.websocket.model

import io.ktor.websocket.WebSocketSession
import java.util.*

data class Member(
    val userId: UUID,
    val sessionId: UUID,
    val socket: WebSocketSession
)
