package ru.cinema.data.db.message.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.timestamp
import ru.cinema.data.db.chat.model.ChatEntity
import ru.cinema.data.db.chat.model.ChatTable
import ru.cinema.data.db.user.model.UserTable
import ru.cinema.domain.chat.model.MessageWithoutUserData
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

object MessageTable : UUIDTable(name = "messages") {
    val createdAt = timestamp(name = "created_at").default(Instant.now())
    val authorId = reference(name = "user_id", foreign = UserTable, onDelete = ReferenceOption.CASCADE)
    val text = text(name = "text")
    val chatId = reference(name = "chat_id", foreign = ChatTable, onDelete = ReferenceOption.CASCADE)
}

class MessageEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    var createdAt by MessageTable.createdAt
    var authorId by MessageTable.authorId
    var text by MessageTable.text
    var chatId by MessageTable.chatId

    var chat by ChatEntity referencedOn MessageTable.chatId

    fun toDomain() = MessageWithoutUserData(
        messageId = id.value,
        creationDateTime = LocalDateTime.ofInstant(createdAt, ZoneOffset.UTC),
        authorId = authorId.value,
        text = text
    )
    companion object : UUIDEntityClass<MessageEntity>(MessageTable)
}
