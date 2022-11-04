package ru.cinema.domain.movie.model

import java.util.UUID

data class MovieContentForm(
    val movieId: UUID,
    val poster: ContentForm?,
    val images: List<ContentForm>,
    val backgroundImage: ContentForm?,
    val foregroundImage: ContentForm?
)
