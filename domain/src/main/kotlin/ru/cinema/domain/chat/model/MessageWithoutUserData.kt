package ru.cinema.domain.chat.model

import java.time.LocalDateTime
import java.util.*

data class MessageWithoutUserData(
    val messageId: UUID,
    val creationDateTime: LocalDateTime,
    val authorId: UUID,
    val text: String
)
