package ru.cinema.domain.chat.model

import java.util.*

data class MessageForm(
    val userId: UUID,
    val chatId: UUID,
    val text: String
)
