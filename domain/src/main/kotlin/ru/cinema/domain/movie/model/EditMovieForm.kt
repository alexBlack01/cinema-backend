package ru.cinema.domain.movie.model

import java.time.LocalDateTime
import java.util.*

data class EditMovieForm(
    val movieId: UUID,
    val name: String?,
    val description: String?,
    val age: String?,
    val tags: List<UUID>?,
    val createdAt: LocalDateTime?
)
