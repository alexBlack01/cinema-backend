package ru.cinema.api.episodecomment.route

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import ru.cinema.api.common.serializations.UUIDSerializer
import java.util.*

@Serializable
@Resource("episodes/{episodeId}/comments")
class Comments(@Serializable(with = UUIDSerializer::class) val episodeId: UUID)

@Serializable
@Resource("episodes/{episodeId}/comments")
class PostComment(@Serializable(with = UUIDSerializer::class) val episodeId: UUID)
