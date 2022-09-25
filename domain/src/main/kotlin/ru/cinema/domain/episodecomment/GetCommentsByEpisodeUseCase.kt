package ru.cinema.domain.episodecomment

import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.episodecomment.model.Comment
import java.util.UUID

interface GetCommentsByEpisodeUseCase : UseCase<UUID, List<Comment>>

class GetCommentsByEpisodeUseCaseImpl(
    private val commentDbDataSource: CommentDbDataSource
) : GetCommentsByEpisodeUseCase {

    override suspend fun execute(param: UUID): Result<List<Comment>> {
        return successResult(commentDbDataSource.getCommentsByEpisode(param))
    }
}
