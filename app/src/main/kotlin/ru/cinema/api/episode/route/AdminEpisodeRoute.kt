package ru.cinema.api.episode.route

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import ru.cinema.api.common.serializations.UUIDSerializer
import java.util.*

@Serializable
@Resource("movies/{movieId}/episodes")
class NewEpisode(@Serializable(with = UUIDSerializer::class) val movieId: UUID)

@Serializable
@Resource("movies/{movieId}/episodes/{episodeId}/files")
class EpisodeFile(
    @Serializable(with = UUIDSerializer::class) val movieId: UUID,
    @Serializable(with = UUIDSerializer::class) val episodeId: UUID
)

@Serializable
@Resource("movies/{movieId}/episodes/{episodeId}")
class UpdateEpisode(
    @Serializable(with = UUIDSerializer::class) val movieId: UUID,
    @Serializable(with = UUIDSerializer::class) val episodeId: UUID
)
