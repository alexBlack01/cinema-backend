package ru.cinema.api.movie.route

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import ru.cinema.api.common.serializations.UUIDSerializer
import java.util.*

@Serializable
@Resource("movies")
class NewMovie

@Serializable
@Resource("movies/{movieId}/images")
class MovieImage(@Serializable(with = UUIDSerializer::class) val movieId: UUID)

@Serializable
@Resource("movies/{movieId}")
class UpdateMovie(@Serializable(with = UUIDSerializer::class) val movieId: UUID)
