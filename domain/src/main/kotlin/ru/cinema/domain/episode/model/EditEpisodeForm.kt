package ru.cinema.domain.episode.model

import java.util.UUID

data class EditEpisodeForm(
    val episodeId: UUID,
    val name: String?,
    val description: String?,
    val director: String?,
    val stars: List<UUID>?,
    val year: Int?,
    val runtime: Int?,
    val filePath: String?
)
