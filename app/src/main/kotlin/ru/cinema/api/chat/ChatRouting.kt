package ru.cinema.api.chat

import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.resources.get
import io.ktor.server.routing.Route
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.server.websocket.application
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.CloseReason
import io.ktor.websocket.close
import org.koin.ktor.ext.inject
import ru.cinema.api.chat.controller.ChatController
import ru.cinema.api.chat.model.ChatResponse
import ru.cinema.api.chat.route.Chats
import ru.cinema.api.chat.websocket.model.SessionData
import ru.cinema.api.common.extensions.getBaseUrl
import ru.cinema.api.common.extensions.principalOrThrow
import ru.cinema.api.common.extensions.respondSuccess
import ru.cinema.auth.model.AuthConstants
import ru.cinema.auth.model.UserPrincipal
import ru.cinema.domain.common.error.InvalidChatId
import java.util.UUID

fun Route.configureChatRouting() {
    val controller by inject<ChatController>()

    authenticate(AuthConstants.USER_AUTH) {
        get<Chats> {
            val userId = call.principalOrThrow<UserPrincipal>().userId

            call.respondSuccess(
                controller.getAllChatsByUser(userId).map {
                    ChatResponse.fromDomain(data = it)
                }
            )
        }

        webSocket("chats/{chatId}/messages") {
            val chatId = UUID.fromString(call.parameters["chatId"])
            val userId = call.principalOrThrow<UserPrincipal>().userId
            val session = call.sessions.get<SessionData>()

            if (session == null) {
                close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session."))
                return@webSocket
            }

            val baseUrl = application.getBaseUrl()

            if (chatId != null) {
                controller.createWebSocketChat(chatId, userId, session, this, baseUrl)
            } else {
                throw InvalidChatId()
            }
        }
    }
}
