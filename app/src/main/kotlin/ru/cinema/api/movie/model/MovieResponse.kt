package ru.cinema.api.movie.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.cinema.api.common.extensions.toResourceUrl
import ru.cinema.api.tag.model.TagResponse
import ru.cinema.domain.movie.model.Movie
import java.util.*

@Serializable
data class MovieResponse(
    @Contextual
    val movieId: UUID,
    val name: String,
    val description: String,
    val age: String,
    val imageUrls: List<String>,
    val poster: String?,
    val tags: List<TagResponse>
) {

    companion object {
        fun fromDomain(data: Movie) = MovieResponse(
            movieId = data.movieId,
            name = data.name,
            description = data.description,
            age = data.age,
            imageUrls = data.imageUrls.map { it.toResourceUrl() },
            poster = data.poster?.toResourceUrl(),
            tags = data.tags.map { TagResponse.fromDomain(it) }
        )
    }
}
