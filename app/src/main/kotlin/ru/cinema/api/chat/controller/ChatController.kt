package ru.cinema.api.chat.controller

import io.ktor.websocket.DefaultWebSocketSession
import ru.cinema.api.chat.websocket.model.SessionData
import ru.cinema.domain.chat.model.Chat
import java.util.UUID

interface ChatController {

    suspend fun getAllChatsByUser(userId: UUID): List<Chat>

    suspend fun createWebSocketChat(
        chatId: UUID,
        userId: UUID,
        session: SessionData,
        webSocketSession: DefaultWebSocketSession,
        baseUrl: String
    )
}
