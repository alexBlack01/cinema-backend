package ru.cinema.api.movie.route

import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.cinema.api.common.serializations.UUIDSerializer
import ru.cinema.api.movie.model.request.MovieTypeDto
import java.util.*

@Serializable
@Resource("movies")
class Movies(
    @SerialName("filter")
    val movieType: MovieTypeDto
)

@Serializable
@Resource("movies/{movieId}/dislike")
class Dislike(@Serializable(with = UUIDSerializer::class) val movieId: UUID)
