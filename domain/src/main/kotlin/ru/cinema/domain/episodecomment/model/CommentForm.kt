package ru.cinema.domain.episodecomment.model

import java.util.UUID

data class CommentForm(
    val userId: UUID,
    val episodeId: UUID,
    val text: String
)
