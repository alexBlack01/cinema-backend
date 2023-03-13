package ru.cinema.domain.user

import ru.cinema.domain.common.usecase.UseCaseWithoutParam
import ru.cinema.domain.user.model.UserProfile

interface GetAllUsersUseCase : UseCaseWithoutParam<List<UserProfile>>

class GetAllUsersUseCaseImpl(
    private val userDbDataSource: UserDbDataSource
) : GetAllUsersUseCase {
    override suspend fun execute(): Result<List<UserProfile>> {
        return successResult(userDbDataSource.getAllUserProfiles())
    }
}
