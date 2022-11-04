package ru.cinema.domain.chat

import ru.cinema.domain.chat.model.Chat
import ru.cinema.domain.chat.model.MessageForm
import ru.cinema.domain.chat.model.MessageWithoutUserData
import java.util.*

interface ChatDbDataSource {

    suspend fun getAllChatsByUser(userId: UUID): List<Chat>
    suspend fun isUserInChat(userId: UUID, chatId: UUID): Boolean
    suspend fun getMessagesByChat(chatId: UUID): List<MessageWithoutUserData>
    suspend fun postMessageByChat(messageForm: MessageForm): MessageWithoutUserData
    suspend fun isChatExist(chatId: UUID): Boolean
}
