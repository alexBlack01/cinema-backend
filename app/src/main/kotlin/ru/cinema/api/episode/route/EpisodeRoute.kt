@file:Suppress("MatchingDeclarationName", "Filename")

package ru.cinema.api.episode.route

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import ru.cinema.api.common.serializations.UUIDSerializer
import java.util.*

@Serializable
@Resource("movies/{movieId}/episodes")
class Episodes(@Serializable(with = UUIDSerializer::class) val movieId: UUID)
