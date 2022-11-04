package ru.cinema.data.db.chat

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SortOrder
import ru.cinema.data.db.chat.model.ChatEntity
import ru.cinema.data.db.chat.model.ChatTable
import ru.cinema.data.db.common.extensions.DatabaseDataSource
import ru.cinema.data.db.message.model.MessageEntity
import ru.cinema.data.db.message.model.MessageTable
import ru.cinema.data.db.user.model.UserEntity
import ru.cinema.domain.chat.ChatDbDataSource
import ru.cinema.domain.chat.model.Chat
import ru.cinema.domain.chat.model.MessageForm
import ru.cinema.domain.chat.model.MessageWithoutUserData
import java.util.*

class ChatDbDataSourceImpl(
    override val db: Database
) : ChatDbDataSource, DatabaseDataSource {

    override suspend fun getAllChatsByUser(userId: UUID): List<Chat> = dbQuery {
        getAllChats(userId)
    }

    override suspend fun isUserInChat(userId: UUID, chatId: UUID): Boolean = dbQuery {
        getAllChats(userId).any { it.chatId == chatId }
    }

    override suspend fun getMessagesByChat(chatId: UUID): List<MessageWithoutUserData> = dbQuery {
        ChatEntity.findById(chatId)?.messages
            ?.orderBy(MessageTable.createdAt to SortOrder.ASC)
            ?.map { it.toDomain() }.orEmpty()
    }

    override suspend fun postMessageByChat(messageForm: MessageForm): MessageWithoutUserData = dbQuery {
        val message = MessageEntity.new {
            this.chatId = ChatEntity[messageForm.chatId].id
            this.authorId = UserEntity[messageForm.userId].id
            this.text = messageForm.text
        }

        message.toDomain()
    }

    override suspend fun isChatExist(chatId: UUID): Boolean = dbQuery {
        getChatById(chatId) != null
    }

    private fun getAllChats(userId: UUID): List<Chat> = UserEntity
        .findById(userId)?.chats
        ?.orderBy(ChatTable.name to SortOrder.ASC)
        ?.map { it.toDomain() }.orEmpty()

    private fun getChatById(chatId: UUID): Chat? = ChatEntity.findById(chatId)?.toDomain()
}
