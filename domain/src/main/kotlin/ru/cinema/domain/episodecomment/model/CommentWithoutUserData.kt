package ru.cinema.domain.episodecomment.model

import java.time.LocalDateTime
import java.util.*

data class CommentWithoutUserData(
    val commentId: UUID,
    val creationDateTime: LocalDateTime,
    val authorId: UUID,
    val text: String
)
