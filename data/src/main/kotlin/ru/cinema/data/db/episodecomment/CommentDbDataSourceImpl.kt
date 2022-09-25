package ru.cinema.data.db.episodecomment

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SortOrder
import ru.cinema.data.db.common.extensions.DatabaseDataSource
import ru.cinema.data.db.episode.model.EpisodeEntity
import ru.cinema.data.db.episodecomment.model.CommentEntity
import ru.cinema.data.db.episodecomment.model.CommentTable
import ru.cinema.domain.episodecomment.CommentDbDataSource
import ru.cinema.domain.episodecomment.model.Comment
import ru.cinema.domain.episodecomment.model.CommentForm
import java.util.*

class CommentDbDataSourceImpl(
    override val db: Database
) : CommentDbDataSource, DatabaseDataSource {
    override suspend fun getCommentsByEpisode(episodeId: UUID): List<Comment> = dbQuery {
        EpisodeEntity.findById(episodeId)?.comments
            ?.orderBy(CommentTable.createdAt to SortOrder.ASC)
            ?.map { it.toDomain() }.orEmpty()
    }

    override suspend fun postCommentByEpisode(
        commentForm: CommentForm,
        authorData: Pair<String, String>
    ): Comment = dbQuery {
        val comment = CommentEntity.new {
            this.episodeId = EpisodeEntity[commentForm.episodeId].id
            this.authorName = authorData.first
            this.authorAvatar = authorData.second
            this.text = commentForm.text
        }

        comment.toDomain()
    }
}
