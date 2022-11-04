package ru.cinema.domain.episode.model

import ru.cinema.domain.movie.model.ContentForm
import java.util.*

data class EpisodeContentForm(
    val episodeId: UUID,
    val images: List<ContentForm>,
    val preview: ContentForm?,
    val filePath: ContentForm?
)
