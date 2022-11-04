package ru.cinema.domain.cover.model

import java.util.UUID

data class Cover(
    val movieId: UUID,
    val backgroundImage: String?,
    val foregroundImage: String?
)
