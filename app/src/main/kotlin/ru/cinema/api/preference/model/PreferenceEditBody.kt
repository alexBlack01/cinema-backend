package ru.cinema.api.preference.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.cinema.domain.tag.model.Tag
import java.util.*

@Serializable
data class PreferenceEditBody(
    @Contextual
    val id: UUID,
    val tagName: String,
    val categoryName: String
) {

    fun toDomain() = Tag(
        id = id,
        tagName = tagName,
        categoryName = categoryName
    )
}
