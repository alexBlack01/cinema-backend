package ru.cinema.domain.preference

import ru.cinema.domain.preference.model.PreferencesForm
import ru.cinema.domain.tag.model.Tag
import java.util.UUID

interface PreferenceDbDataSource {

    suspend fun getPreferencesByUser(userId: UUID): List<Tag>

    suspend fun updatePreferencesByUser(preferencesForm: PreferencesForm)
}
