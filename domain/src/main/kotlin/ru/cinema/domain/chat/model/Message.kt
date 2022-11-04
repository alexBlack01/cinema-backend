package ru.cinema.domain.chat.model

import java.time.LocalDateTime
import java.util.*

data class Message(
    val messageId: UUID,
    val creationDateTime: LocalDateTime,
    val authorName: String,
    val authorAvatar: String,
    val text: String
)
