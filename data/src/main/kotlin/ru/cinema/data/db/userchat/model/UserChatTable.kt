package ru.cinema.data.db.userchat.model

import org.jetbrains.exposed.sql.Table
import ru.cinema.data.db.chat.model.ChatTable
import ru.cinema.data.db.user.model.UserTable

object UserChatTable : Table(name = "users_chats") {
    val userId = reference(name = "user_id", foreign = UserTable)
    val chatId = reference(name = "chat_id", foreign = ChatTable)
    override val primaryKey = PrimaryKey(userId, chatId, name = "PK_UserChat_user_id_chat_id")
}
