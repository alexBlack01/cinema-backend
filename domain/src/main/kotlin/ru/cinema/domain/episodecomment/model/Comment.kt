package ru.cinema.domain.episodecomment.model

import java.time.LocalDateTime
import java.util.*

data class Comment(
    val commentId: UUID,
    val creationDateTime: LocalDateTime,
    val authorName: String,
    val authorAvatar: String,
    val text: String
)
