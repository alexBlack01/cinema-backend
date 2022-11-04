@file:Suppress("MatchingDeclarationName", "Filename")

package ru.cinema.api.episodecomment.route

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import ru.cinema.api.common.serializations.UUIDSerializer
import java.util.*

@Serializable
@Resource("episodes/{episodeId}/comments/{commentId}")
class DeleteComment(
    @Serializable(with = UUIDSerializer::class) val episodeId: UUID,
    @Serializable(with = UUIDSerializer::class) val commentId: UUID
)
