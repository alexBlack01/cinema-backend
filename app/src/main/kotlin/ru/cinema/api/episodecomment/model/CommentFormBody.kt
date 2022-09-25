package ru.cinema.api.episodecomment.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.cinema.domain.episodecomment.model.CommentForm
import java.util.*

@Serializable
data class CommentFormBody(
    @Contextual
    val userId: UUID,
    @Contextual
    val episodeId: UUID,
    val text: String
) {
    fun toDomain() = CommentForm(
        userId = userId,
        episodeId = episodeId,
        text = text
    )
}
