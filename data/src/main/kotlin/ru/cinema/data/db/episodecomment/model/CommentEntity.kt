package ru.cinema.data.db.episodecomment.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.datetime
import ru.cinema.data.db.episode.model.EpisodeEntity
import ru.cinema.data.db.episode.model.EpisodeTable
import ru.cinema.domain.episodecomment.model.Comment
import java.time.LocalDateTime
import java.util.*

object CommentTable : UUIDTable(name = "comments") {
    val createdAt = datetime(name = "created_at").clientDefault { LocalDateTime.now() }
    val authorName = varchar(name = "author_name", length = 250)
    val authorAvatar = varchar(name = "author_avatar", length = 200)
    val text = text(name = "text")
    val episodeId = reference(name = "episode_id", foreign = EpisodeTable, onDelete = ReferenceOption.CASCADE)
}

class CommentEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    var createdAt by CommentTable.createdAt
    var authorName by CommentTable.authorName
    var authorAvatar by CommentTable.authorAvatar
    var text by CommentTable.text
    var episodeId by CommentTable.episodeId

    var episode by EpisodeEntity referencedOn CommentTable.episodeId

    fun toDomain() = Comment(
        commentId = id.value,
        creationDateTime = createdAt,
        authorName = authorName,
        authorAvatar = authorAvatar,
        text = text
    )
    companion object : UUIDEntityClass<CommentEntity>(CommentTable)
}
