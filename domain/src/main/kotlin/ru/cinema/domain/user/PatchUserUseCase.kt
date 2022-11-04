package ru.cinema.domain.user

import ru.cinema.domain.common.error.UserNotFound
import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.user.model.EditUserProfile
import ru.cinema.domain.user.model.UserProfile

interface PatchUserUseCase : UseCase<EditUserProfile, UserProfile>

class PatchUserUseCaseImpl(
    private val userDbDataSource: UserDbDataSource
) : PatchUserUseCase {

    override suspend fun execute(param: EditUserProfile): Result<UserProfile> {
        userDbDataSource.getUserById(param.id) ?: throw UserNotFound()

        val newUser = userDbDataSource.patchUserProfile(param) ?: throw UserNotFound()
        return successResult(newUser)
    }
}
