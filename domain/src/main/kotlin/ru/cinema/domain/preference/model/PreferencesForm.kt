package ru.cinema.domain.preference.model

import ru.cinema.domain.tag.model.Tag
import java.util.UUID

data class PreferencesForm(
    val userId: UUID,
    val preferences: List<Tag>
)
