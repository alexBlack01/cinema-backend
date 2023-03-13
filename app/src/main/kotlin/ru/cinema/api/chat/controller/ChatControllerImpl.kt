package ru.cinema.api.chat.controller

import io.ktor.websocket.DefaultWebSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consumeEach
import ru.cinema.api.chat.websocket.WebSocketManager
import ru.cinema.api.chat.websocket.model.Member
import ru.cinema.api.chat.websocket.model.SessionData
import ru.cinema.domain.chat.CheckChatExistUseCase
import ru.cinema.domain.chat.GetAllChatsByUserUseCase
import ru.cinema.domain.chat.GetMessagesByChatUseCase
import ru.cinema.domain.chat.PostMessageByChatUseCase
import ru.cinema.domain.chat.model.Chat
import ru.cinema.domain.chat.model.MessageForm
import ru.cinema.domain.common.error.InvalidChatId
import java.util.*

class ChatControllerImpl(
    private val getAllChatsByUserUseCase: GetAllChatsByUserUseCase,
    private val getMessagesByChatUseCase: GetMessagesByChatUseCase,
    private val postMessageByChatUseCase: PostMessageByChatUseCase,
    private val checkChatExistUseCase: CheckChatExistUseCase
) : ChatController {

    override suspend fun getAllChatsByUser(userId: UUID): List<Chat> {
        return getAllChatsByUserUseCase(userId).getOrThrow()
    }

    override suspend fun createWebSocketChat(
        chatId: UUID,
        userId: UUID,
        session: SessionData,
        webSocketSession: DefaultWebSocketSession,
        baseUrl: String
    ) {
        if (checkChatExistUseCase(chatId).getOrThrow()) {
            try {
                WebSocketManager.onJoin(
                    member = Member(
                        userId = userId,
                        sessionId = session.sessionId,
                        socket = webSocketSession),
                    chatId = chatId
                )

                val messages = getMessagesByChatUseCase(userId to chatId).getOrThrow()

                if (messages.isNotEmpty()) {
                    WebSocketManager.sendAllMessagesInChat(messages, webSocketSession)
                }

                webSocketSession.incoming.consumeEach { frame ->
                    if (frame is Frame.Text) {
                        val message = postMessageByChatUseCase(
                            MessageForm(
                                userId = userId,
                                chatId = chatId,
                                text = frame.readText()
                            )
                        ).getOrThrow()

                        WebSocketManager.sendMessageToAll(message, chatId)
                    }
                }
            } finally {
                WebSocketManager.tryDisconnect(userId, chatId)
            }
        } else {
            throw InvalidChatId()
        }
    }
}
