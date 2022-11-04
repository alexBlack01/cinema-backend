package ru.cinema.api.chat.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.cinema.domain.chat.model.Chat
import java.util.UUID

@Serializable
data class ChatResponse(
    @Contextual
    val chatId: UUID,
    val chatName: String
) {

    companion object {
        fun fromDomain(data: Chat) = ChatResponse(
            chatId = data.chatId,
            chatName = data.chatName
        )
    }
}
