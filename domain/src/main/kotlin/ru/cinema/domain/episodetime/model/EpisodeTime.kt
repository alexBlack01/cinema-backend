package ru.cinema.domain.episodetime.model

import java.util.UUID

data class EpisodeTime(
    val episodeId: UUID,
    val userId: UUID,
    val timeInSeconds: Int
)
