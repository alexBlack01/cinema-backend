package ru.cinema.domain.episode.model

import java.util.*

data class ShortEpisodeInfo(
    val episodeId: UUID,
    val movieId: UUID,
    val episodeName: String,
    val preview: String?,
    val filePath: String?
)
