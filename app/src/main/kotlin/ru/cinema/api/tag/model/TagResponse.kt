package ru.cinema.api.tag.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.cinema.domain.tag.model.Tag
import java.util.*

@Serializable
data class TagResponse(
    @Contextual
    val id: UUID,
    val tagName: String,
    val categoryName: String
) {

    companion object {
        fun fromDomain(data: Tag) = TagResponse(
            id = data.id,
            tagName = data.tagName,
            categoryName = data.categoryName
        )
    }
}
