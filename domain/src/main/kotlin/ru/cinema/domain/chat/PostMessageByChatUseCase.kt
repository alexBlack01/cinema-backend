package ru.cinema.domain.chat

import ru.cinema.domain.chat.model.Message
import ru.cinema.domain.chat.model.MessageForm
import ru.cinema.domain.common.error.ChatNotFound
import ru.cinema.domain.common.error.InvalidUserCredentials
import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.user.UserDbDataSource

interface PostMessageByChatUseCase : UseCase<MessageForm, Message>

class PostMessageByChatUseCaseImpl(
    private val userDbDataSource: UserDbDataSource,
    private val chatDbDataSource: ChatDbDataSource
) : PostMessageByChatUseCase {

    override suspend fun execute(param: MessageForm): Result<Message> {
        val user = userDbDataSource.getUserById(param.userId) ?: throw InvalidUserCredentials()

        if (!chatDbDataSource.isUserInChat(param.userId, param.chatId)) {
            throw ChatNotFound()
        }

        val messageWithoutUserData = chatDbDataSource.postMessageByChat(param)

        return successResult(
            Message(
                messageId = messageWithoutUserData.messageId,
                creationDateTime = messageWithoutUserData.creationDateTime,
                authorName = "${user.lastName} ${user.lastName}",
                authorAvatar = user.avatar.orEmpty(),
                text = messageWithoutUserData.text
            )
        )
    }
}
