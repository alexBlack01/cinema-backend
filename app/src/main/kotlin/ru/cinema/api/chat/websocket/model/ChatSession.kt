package ru.cinema.api.chat.websocket.model

import java.util.*
import java.util.concurrent.ConcurrentHashMap

data class ChatSession(
    val chatId: UUID,
    val members: ConcurrentHashMap<UUID, Member>? = null
)
