package ru.cinema.domain.preference

import ru.cinema.domain.common.error.UserNotFound
import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.preference.model.PreferencesForm
import ru.cinema.domain.user.UserDbDataSource

interface PutPreferenceByUserUseCase : UseCase<PreferencesForm, Unit>

class PutPreferenceByUserUseCaseImpl(
    private val preferenceDbDataSource: PreferenceDbDataSource,
    private val userDbDataSource: UserDbDataSource
) : PutPreferenceByUserUseCase {

    override suspend fun execute(param: PreferencesForm): Result<Unit> {
        userDbDataSource.getUserById(param.userId) ?: throw UserNotFound()

        return successResult(preferenceDbDataSource.updatePreferencesByUser(param))
    }
}
