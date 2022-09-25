package ru.cinema.data.db.episode.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import ru.cinema.data.db.episodecomment.model.CommentEntity
import ru.cinema.data.db.episodecomment.model.CommentTable
import ru.cinema.data.db.episodestar.model.EpisodeStarTable
import ru.cinema.data.db.image.model.EpisodeImageEntity
import ru.cinema.data.db.image.model.EpisodeImageTable
import ru.cinema.data.db.movie.model.MovieEntity
import ru.cinema.data.db.movie.model.MovieTable
import ru.cinema.data.db.star.model.StarEntity
import ru.cinema.domain.episode.model.Episode
import java.util.*

object EpisodeTable : UUIDTable(name = "episodes") {
    val name = varchar(name = "name", length = 150)
    val description = text(name = "description")
    val director = varchar(name = "director", length = 150)
    val year = varchar(name = "year", length = 10)
    val runtime = varchar(name = "runtime", length = 50)
    val preview = varchar(name = "preview", length = 150)
    val filePath = varchar(name = "file_path", length = 150)
    val movieId = reference(name = "movie_id", foreign = MovieTable, onDelete = ReferenceOption.CASCADE)
}

class EpisodeEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    var name by EpisodeTable.name
    var description by EpisodeTable.description
    var director by EpisodeTable.director
    var year by EpisodeTable.year
    var runtime by EpisodeTable.runtime
    var preview by EpisodeTable.preview
    var filePath by EpisodeTable.filePath

    val movie by MovieEntity referencedOn EpisodeTable.movieId
    val images by EpisodeImageEntity referrersOn EpisodeImageTable.episodeId
    val stars by StarEntity via EpisodeStarTable
    val comments by CommentEntity referrersOn CommentTable.episodeId

    fun toDomain() = Episode(
        episodeId = id.value,
        name = name,
        description = description,
        director = director,
        stars = stars.map { it.name },
        year = year.toInt(),
        imageUrls = images.map { it.url },
        runtime = runtime.toInt(),
        preview = preview,
        filePath = filePath
    )
    companion object : UUIDEntityClass<EpisodeEntity>(EpisodeTable)
}
