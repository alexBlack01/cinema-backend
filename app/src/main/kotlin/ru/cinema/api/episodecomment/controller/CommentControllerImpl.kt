package ru.cinema.api.episodecomment.controller

import ru.cinema.api.episodecomment.model.CommentFormBody
import ru.cinema.domain.episodecomment.DeleteCommentByAdminUseCase
import ru.cinema.domain.episodecomment.GetCommentsByEpisodeUseCase
import ru.cinema.domain.episodecomment.PostCommentByEpisodeUseCase
import ru.cinema.domain.episodecomment.model.Comment
import java.util.*

class CommentControllerImpl(
    private val getCommentsByEpisodeUseCase: GetCommentsByEpisodeUseCase,
    private val postCommentByEpisodeUseCase: PostCommentByEpisodeUseCase,
    private val deleteCommentByAdminUseCase: DeleteCommentByAdminUseCase
) : CommentController {
    override suspend fun getCommentsByEpisode(episodeId: UUID): List<Comment> {
        return getCommentsByEpisodeUseCase(episodeId).getOrThrow()
    }

    override suspend fun postCommentByEpisode(commentForm: CommentFormBody): Comment {
        return postCommentByEpisodeUseCase(commentForm.toDomain()).getOrThrow()
    }

    override suspend fun deleteCommentByAdmin(episodeId: UUID, commentId: UUID) {
        deleteCommentByAdminUseCase(episodeId to commentId).getOrThrow()
    }
}
