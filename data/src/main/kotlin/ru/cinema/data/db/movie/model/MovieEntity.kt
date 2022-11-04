package ru.cinema.data.db.movie.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.timestamp
import ru.cinema.data.db.collection.model.CollectionEntity
import ru.cinema.data.db.collectionmovie.model.CollectionMovieTable
import ru.cinema.data.db.dislike.model.DislikeTable
import ru.cinema.data.db.episode.model.EpisodeEntity
import ru.cinema.data.db.episode.model.EpisodeTable
import ru.cinema.data.db.image.model.ImageEntity
import ru.cinema.data.db.image.model.ImageTable
import ru.cinema.data.db.tag.model.TagEntity
import ru.cinema.data.db.tagmovie.model.TagMovieTable
import ru.cinema.data.db.user.model.UserEntity
import ru.cinema.domain.cover.model.Cover
import ru.cinema.domain.movie.model.Movie
import ru.cinema.domain.movie.model.ShortMovieInfo
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

object MovieTable : UUIDTable(name = "movies") {
    val name = varchar(name = "name", length = 150)
    val description = text(name = "description")
    val age = varchar(name = "age", length = 5)
    val poster = varchar(name = "poster", length = 200).nullable()
    val backgroundImage = varchar(name = "background_image", length = 200).nullable()
    val foregroundImage = varchar(name = "foreground_image", length = 200).nullable()
    val createdAt = timestamp(name = "created_at")
    val viewCount = integer(name = "view_count").default(0)
}

class MovieEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    var name by MovieTable.name
    var description by MovieTable.description
    var age by MovieTable.age
    var poster by MovieTable.poster
    var backgroundImage by MovieTable.backgroundImage
    var foregroundImage by MovieTable.foregroundImage
    var createdAt by MovieTable.createdAt
    var viewCount by MovieTable.viewCount

    val images by ImageEntity referrersOn ImageTable.movieId
    val tags by TagEntity via TagMovieTable
    val episodes by EpisodeEntity referrersOn EpisodeTable.movieId
    val collections by CollectionEntity via CollectionMovieTable
    val users by UserEntity via DislikeTable

    fun toDomain() = Movie(
        movieId = id.value,
        name = name,
        description = description,
        age = age,
        imageUrls = images.map { it.url },
        poster = poster,
        tags = tags.map { it.toDomain() },
        backgroundImage = backgroundImage,
        foregroundImage = foregroundImage,
        createdAt = LocalDateTime.ofInstant(createdAt, ZoneOffset.UTC)
    )

    fun toDomainShortMovieInfo() = ShortMovieInfo(
        movieId = id.value,
        name = name
    )

    fun toDomainForCover() = Cover(
        movieId = id.value,
        backgroundImage = backgroundImage,
        foregroundImage = foregroundImage
    )
    companion object : UUIDEntityClass<MovieEntity>(MovieTable)
}
