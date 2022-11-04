package ru.cinema.domain.episodetime.model

import java.util.*

data class EpisodeWithTime(
    val episodeId: UUID,
    val movieId: UUID?,
    val episodeName: String?,
    val movieName: String?,
    val preview: String?,
    val filePath: String?,
    val time: Int
)
