package ru.cinema.domain.episode.model

import java.util.*

data class Episode(
    val episodeId: UUID,
    val name: String,
    val description: String,
    val director: String,
    val stars: List<String>,
    val year: Int,
    val imageUrls: List<String>,
    val runtime: Int,
    val preview: String,
    val filePath: String
)
