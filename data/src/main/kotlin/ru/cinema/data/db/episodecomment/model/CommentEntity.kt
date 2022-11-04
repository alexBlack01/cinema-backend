package ru.cinema.data.db.episodecomment.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.timestamp
import ru.cinema.data.db.episode.model.EpisodeEntity
import ru.cinema.data.db.episode.model.EpisodeTable
import ru.cinema.data.db.user.model.UserEntity
import ru.cinema.data.db.user.model.UserTable
import ru.cinema.domain.episodecomment.model.CommentWithoutUserData
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

object CommentTable : UUIDTable(name = "comments") {
    val createdAt = timestamp(name = "created_at").default(Instant.now())
    val authorId = reference(name = "author_id", foreign = UserTable, onDelete = ReferenceOption.CASCADE)
    val text = text(name = "text")
    val episodeId = reference(name = "episode_id", foreign = EpisodeTable, onDelete = ReferenceOption.CASCADE)
}

class CommentEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    var createdAt by CommentTable.createdAt
    var authorId by CommentTable.authorId
    var text by CommentTable.text
    var episodeId by CommentTable.episodeId

    var episode by EpisodeEntity referencedOn CommentTable.episodeId
    var author by UserEntity referencedOn CommentTable.authorId

    fun toDomain() = CommentWithoutUserData(
        commentId = id.value,
        creationDateTime = LocalDateTime.ofInstant(createdAt, ZoneOffset.UTC),
        authorId = authorId.value,
        text = text
    )
    companion object : UUIDEntityClass<CommentEntity>(CommentTable)
}
