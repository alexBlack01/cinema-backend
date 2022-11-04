package ru.cinema.domain.episodetime.model

import java.util.UUID

data class EpisodeHistoryForm(
    val episodeId: UUID,
    val time: Int
)
