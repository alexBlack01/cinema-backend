package ru.cinema.domain.episodecomment

import ru.cinema.domain.episodecomment.model.CommentForm
import ru.cinema.domain.episodecomment.model.CommentWithoutUserData
import java.util.*

interface CommentDbDataSource {
    suspend fun getCommentsByEpisode(episodeId: UUID): List<CommentWithoutUserData>

    suspend fun getCommentById(commentId: UUID): CommentWithoutUserData?

    suspend fun postCommentByEpisode(commentForm: CommentForm): CommentWithoutUserData

    suspend fun deleteCommentById(episodeId: UUID, commentId: UUID)
}
