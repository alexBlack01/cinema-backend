package ru.cinema.api.episodecomment.controller

import ru.cinema.api.episodecomment.model.CommentFormBody
import ru.cinema.domain.episodecomment.model.Comment
import java.util.*

interface CommentController {
    suspend fun getCommentsByEpisode(episodeId: UUID): List<Comment>

    suspend fun postCommentByEpisode(commentForm: CommentFormBody): Comment

    suspend fun deleteCommentByAdmin(episodeId: UUID, commentId: UUID)
}
