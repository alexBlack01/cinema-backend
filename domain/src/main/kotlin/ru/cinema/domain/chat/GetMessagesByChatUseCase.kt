package ru.cinema.domain.chat

import ru.cinema.domain.chat.model.Message
import ru.cinema.domain.chat.model.MessageWithoutUserData
import ru.cinema.domain.common.error.ChatNotFound
import ru.cinema.domain.common.error.InvalidUserCredentials
import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.user.UserDbDataSource
import java.util.UUID

interface GetMessagesByChatUseCase : UseCase<Pair<UUID, UUID>, List<Message>>

class GetMessagesByChatUseCaseImpl(
    private val userDbDataSource: UserDbDataSource,
    private val chatDbDataSource: ChatDbDataSource
) : GetMessagesByChatUseCase {

    /**
     * @param param.first is user id
     * @param param.second is chat id
     */
    override suspend fun execute(param: Pair<UUID, UUID>): Result<List<Message>> {
        userDbDataSource.getUserById(param.first) ?: throw InvalidUserCredentials()

        if (!chatDbDataSource.isUserInChat(param.first, param.second)) {
            throw ChatNotFound()
        }

        val messages = chatDbDataSource.getMessagesByChat(param.second)

        val messagesWithUserData = addUserDataInMessage(messages)

        return successResult(messagesWithUserData)
    }

    private suspend fun addUserDataInMessage(messages: List<MessageWithoutUserData>): List<Message> {
        val messagesWithUserData: MutableList<Message> = mutableListOf()

        messages.forEach { message ->
            val author = userDbDataSource.getUserById(message.authorId)

            messagesWithUserData.add(
                Message(
                    messageId = message.messageId,
                    creationDateTime = message.creationDateTime,
                    authorName = "${author?.lastName} ${author?.lastName}",
                    authorAvatar = author?.avatar.orEmpty(),
                    text = message.text
                )
            )
        }

        return messagesWithUserData
    }
}
