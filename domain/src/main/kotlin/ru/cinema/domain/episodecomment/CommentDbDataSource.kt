package ru.cinema.domain.episodecomment

import ru.cinema.domain.episodecomment.model.Comment
import ru.cinema.domain.episodecomment.model.CommentForm
import java.util.*

interface CommentDbDataSource {
    suspend fun getCommentsByEpisode(episodeId: UUID): List<Comment>

    suspend fun postCommentByEpisode(commentForm: CommentForm, authorData: Pair<String, String>): Comment
}
