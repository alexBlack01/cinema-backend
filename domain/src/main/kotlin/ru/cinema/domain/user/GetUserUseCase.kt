package ru.cinema.domain.user

import ru.cinema.domain.common.error.UserNotFound
import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.user.model.UserProfile
import java.util.*

interface GetUserUseCase : UseCase<UUID, UserProfile>

class GetUserUseCaseImpl(
    private val userDbDataSource: UserDbDataSource
) : GetUserUseCase {

    /**
     * @param param is user ID
     */
    override suspend fun execute(param: UUID): Result<UserProfile> {
        val user = userDbDataSource.getUserById(param) ?: throw UserNotFound()
        return successResult(user)
    }
}
