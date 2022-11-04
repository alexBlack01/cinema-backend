package ru.cinema.api.episodecomment.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.cinema.api.common.extensions.toResourceUrl
import ru.cinema.domain.episodecomment.model.Comment
import java.time.LocalDateTime
import java.util.*

@Serializable
data class CommentResponse(
    @Contextual
    val commentId: UUID,
    @Contextual
    val creationDateTime: LocalDateTime,
    val authorName: String,
    val authorAvatar: String,
    val text: String

) {

    companion object {
        fun fromDomain(data: Comment, baseUrl: String, uploadFolder: String, folderUrl: String) = CommentResponse(
            commentId = data.commentId,
            creationDateTime = data.creationDateTime,
            authorName = data.authorName,
            authorAvatar = data.authorAvatar.toResourceUrl(baseUrl, uploadFolder, folderUrl),
            text = data.text
        )
    }
}
