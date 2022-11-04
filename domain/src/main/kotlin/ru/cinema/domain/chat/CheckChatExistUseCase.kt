package ru.cinema.domain.chat

import ru.cinema.domain.common.usecase.UseCase
import java.util.UUID

interface CheckChatExistUseCase : UseCase<UUID, Boolean>

class CheckChatExistUseCaseImpl(
    private val chatDbDataSource: ChatDbDataSource
) : CheckChatExistUseCase {
    /**
     * @param param is chat ID
     */
    override suspend fun execute(param: UUID): Result<Boolean> {
        return successResult(chatDbDataSource.isChatExist(param))
    }
}
