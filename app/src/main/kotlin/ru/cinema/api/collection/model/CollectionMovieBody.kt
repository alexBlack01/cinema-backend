package ru.cinema.api.collection.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CollectionMovieBody(
    @Contextual
    val movieId: UUID
)
