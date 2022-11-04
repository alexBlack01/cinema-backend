package ru.cinema.data.db.chat.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import ru.cinema.data.db.message.model.MessageEntity
import ru.cinema.data.db.message.model.MessageTable
import ru.cinema.data.db.user.model.UserEntity
import ru.cinema.data.db.userchat.model.UserChatTable
import ru.cinema.domain.chat.model.Chat
import java.util.*

object ChatTable : UUIDTable(name = "chats") {
    val name = varchar(name = "name", length = 150)
}

class ChatEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    var name by ChatTable.name

    val users by UserEntity via UserChatTable
    val messages by MessageEntity referrersOn MessageTable.chatId

    fun toDomain() = Chat(
        chatId = id.value,
        chatName = name
    )

    companion object : UUIDEntityClass<ChatEntity>(ChatTable)
}
