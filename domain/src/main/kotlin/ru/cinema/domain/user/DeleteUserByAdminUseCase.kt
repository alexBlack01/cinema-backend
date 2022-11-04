package ru.cinema.domain.user

import ru.cinema.domain.common.error.UserNotFound
import ru.cinema.domain.common.usecase.UseCase
import java.util.UUID

interface DeleteUserByAdminUseCase : UseCase<UUID, Unit>

class DeleteUserByAdminUseCaseImpl(
    private val userDbDataSource: UserDbDataSource
) : DeleteUserByAdminUseCase {

    /**
     * @param param is user id
     */
    override suspend fun execute(param: UUID): Result<Unit> {
        userDbDataSource.getUserById(param) ?: throw UserNotFound()

        return successResult(userDbDataSource.deleteUserById(param))
    }
}
