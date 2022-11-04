@file:Suppress("MatchingDeclarationName", "Filename")

package ru.cinema.api.episodetime.route

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import ru.cinema.api.common.serializations.UUIDSerializer
import java.util.*

@Serializable
@Resource("episodes/{episodeId}/time")
class EpisodeTime(@Serializable(with = UUIDSerializer::class) val episodeId: UUID)
