package ru.cinema.domain.chat

import ru.cinema.domain.chat.model.Chat
import ru.cinema.domain.common.usecase.UseCase
import java.util.*

interface GetAllChatsByUserUseCase : UseCase<UUID, List<Chat>>

class GetAllChatsByUserUseCaseImpl(
    private val chatDbDataSource: ChatDbDataSource
) : GetAllChatsByUserUseCase {

    /**
     * @param param is user id
     */
    override suspend fun execute(param: UUID): Result<List<Chat>> {
        return successResult(chatDbDataSource.getAllChatsByUser(param))
    }
}
