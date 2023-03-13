package ru.cinema.api.chat.websocket

import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.cinema.api.chat.model.MessageResponse
import ru.cinema.api.chat.websocket.model.ChatSession
import ru.cinema.api.chat.websocket.model.Member
import ru.cinema.domain.chat.model.Message
import ru.cinema.domain.common.error.MemberAlreadyExists
import java.util.*

object WebSocketManager {

    private val chatSessions = mutableListOf<ChatSession>()

    fun onJoin(member: Member, chatId: UUID) {
        if (!chatSessions.any { it.chatId == chatId }) {
            createChatSession(chatId)
        }

        val isMemberInChat = chatSessions.find { it.chatId == chatId }?.members?.containsKey(member.userId)
        if (isMemberInChat != null) {
            throw MemberAlreadyExists()
        }

        chatSessions.find { it.chatId == chatId }?.members?.set(
            member.userId, Member(
                userId = member.userId,
                sessionId = member.sessionId,
                socket = member.socket
            )
        )
    }

    suspend fun sendAllMessagesInChat(messages: List<Message>, socket: WebSocketSession) {
        messages.forEach { message ->

            val messageResponse = MessageResponse.fromDomain(message)

            val parsedMessage = Json.encodeToString(messageResponse)
            socket.send(Frame.Text(parsedMessage))
        }
    }

    suspend fun sendMessageToAll(message: Message, chatId: UUID) {
        chatSessions.find { it.chatId == chatId }?.members?.values?.forEach { member ->

            val messageResponse = MessageResponse.fromDomain(message)

            val parsedMessage = Json.encodeToString(messageResponse)
            member.socket.send(Frame.Text(parsedMessage))
        }
    }

    suspend fun tryDisconnect(userId: UUID, chatId: UUID) {
        chatSessions.find { it.chatId == chatId }?.members?.get(userId)?.socket?.close()

        val isMemberInChat = chatSessions.find { it.chatId == chatId }?.members?.containsKey(userId)
        if (isMemberInChat != null) {
            chatSessions.find { it.chatId == chatId }?.members?.remove(userId)
        }

        if (chatSessions.find { it.chatId == chatId }?.members?.isEmpty() == true) {
            deleteChatSession(chatId)
        }
    }

    private fun createChatSession(chatId: UUID) {
        chatSessions.add(
            ChatSession(chatId)
        )
    }

    private fun deleteChatSession(chatId: UUID) {
        chatSessions.removeAll { it.chatId == chatId }
    }
}
