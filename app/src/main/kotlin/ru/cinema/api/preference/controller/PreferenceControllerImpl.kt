package ru.cinema.api.preference.controller

import ru.cinema.api.preference.model.PreferenceEditBody
import ru.cinema.domain.preference.GetPreferencesByUserUseCase
import ru.cinema.domain.preference.PutPreferenceByUserUseCase
import ru.cinema.domain.preference.model.PreferencesForm
import ru.cinema.domain.tag.model.Tag
import java.util.*

class PreferenceControllerImpl(
    private val getPreferencesByUserUseCase: GetPreferencesByUserUseCase,
    private val putPreferenceByUserUseCase: PutPreferenceByUserUseCase
) : PreferenceController {
    override suspend fun getPreferencesByUser(userId: UUID): List<Tag> {
        return getPreferencesByUserUseCase(userId).getOrThrow()
    }

    override suspend fun putPreferencesByUser(userId: UUID, preferences: List<PreferenceEditBody>) {
        putPreferenceByUserUseCase(PreferencesForm(userId, preferences.map { it.toDomain() })).getOrThrow()
    }
}
