package ru.cinema.domain.auth

import ru.cinema.domain.auth.token.AuthTokenDbDataSource
import ru.cinema.domain.common.error.UserNotFound
import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.user.UserDbDataSource
import java.util.UUID

interface LogoutUseCase : UseCase<UUID, Unit>

class LogoutUseCaseImpl(
    private val authTokenDbDataSource: AuthTokenDbDataSource,
    private val userDbDataSource: UserDbDataSource
) : LogoutUseCase {
    /**
     * @param param is user id
     */
    override suspend fun execute(param: UUID): Result<Unit> {
        userDbDataSource.getUserById(param) ?: throw UserNotFound()

        return successResult(authTokenDbDataSource.logoutUser(param))
    }
}
