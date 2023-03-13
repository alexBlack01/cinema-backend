package ru.cinema.api.chat.model

import kotlinx.serialization.Serializable
import ru.cinema.api.common.extensions.toResourceUrl
import ru.cinema.api.common.serializations.LocalDateTimeSerializer
import ru.cinema.api.common.serializations.UUIDSerializer
import ru.cinema.domain.chat.model.Message
import java.time.LocalDateTime
import java.util.*

@Serializable
data class MessageResponse(
    @Serializable(with = UUIDSerializer::class)
    val messageId: UUID,
    @Serializable(with = LocalDateTimeSerializer::class)
    val creationDateTime: LocalDateTime,
    val authorName: String,
    val authorAvatar: String,
    val text: String

) {

    companion object {
        fun fromDomain(data: Message) = MessageResponse(
            messageId = data.messageId,
            creationDateTime = data.creationDateTime,
            authorName = data.authorName,
            authorAvatar = data.authorAvatar.toResourceUrl(),
            text = data.text
        )
    }
}
