package ru.cinema.domain.movie.model

import ru.cinema.domain.tag.model.Tag
import java.util.*

data class Movie(
    val movieId: UUID,
    val name: String,
    val description: String,
    val age: String,
    val imageUrls: List<String>,
    val poster: String,
    val tags: List<Tag>,
    val backgroundImage: String,
    val foregroundImage: String
)
