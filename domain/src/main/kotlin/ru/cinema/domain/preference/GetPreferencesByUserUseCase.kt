package ru.cinema.domain.preference

import ru.cinema.domain.common.error.UserNotFound
import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.tag.model.Tag
import ru.cinema.domain.user.UserDbDataSource
import java.util.UUID

interface GetPreferencesByUserUseCase : UseCase<UUID, List<Tag>>

class GetPreferencesByUserUseCaseImpl(
    private val preferenceDbDataSource: PreferenceDbDataSource,
    private val userDbDataSource: UserDbDataSource
) : GetPreferencesByUserUseCase {

    /**
     * @param param is user ID
     */
    override suspend fun execute(param: UUID): Result<List<Tag>> {
        userDbDataSource.getUserById(param) ?: throw UserNotFound()

        return successResult(preferenceDbDataSource.getPreferencesByUser(param))
    }
}
