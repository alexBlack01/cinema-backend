package ru.cinema.api.preference.controller

import ru.cinema.api.preference.model.PreferenceEditBody
import ru.cinema.domain.tag.model.Tag
import java.util.UUID

interface PreferenceController {

    suspend fun getPreferencesByUser(userId: UUID): List<Tag>

    suspend fun putPreferencesByUser(userId: UUID, preferences: List<PreferenceEditBody>)
}
