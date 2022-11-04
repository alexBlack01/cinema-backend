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

    @Suppress("LongParameterList")
    companion object {
        fun fromDomain(
            data: Movie,
            baseUrl: String,
            uploadFolder: String,
            movieImageFolder: String,
            posterFolder: String
        ) = MovieResponse(
            movieId = data.movieId,
            name = data.name,
            description = data.description,
            age = data.age,
            imageUrls = data.imageUrls.map { it.toResourceUrl(baseUrl, uploadFolder, movieImageFolder) },
            poster = data.poster?.toResourceUrl(baseUrl, uploadFolder, posterFolder),
            tags = data.tags.map { TagResponse.fromDomain(it) }
        )
    }
}
