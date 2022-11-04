package ru.cinema.domain.collectionmovie.model

import java.util.UUID

data class CollectionMovieForm(
    val userId: UUID,
    val collectionId: UUID,
    val movieId: UUID
)
