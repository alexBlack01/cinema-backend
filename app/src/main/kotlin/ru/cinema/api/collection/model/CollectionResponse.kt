package ru.cinema.api.collection.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.cinema.domain.collection.model.Collection
import java.util.UUID

@Serializable
data class CollectionResponse(
    @Contextual
    val collectionId: UUID,
    val name: String
) {
    companion object {
        fun fromDomain(data: Collection) = CollectionResponse(
            collectionId = data.collectionId,
            name = data.name
        )
    }
}
