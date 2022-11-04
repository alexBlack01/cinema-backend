package ru.cinema.data.db.cover.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import ru.cinema.data.db.movie.model.MovieTable
import ru.cinema.domain.cover.model.Cover
import java.util.*

object CoverTable : UUIDTable(name = "cover") {
    val backgroundImage = varchar(name = "background_image", length = 200).nullable()
    val foregroundImage = varchar(name = "foreground_image", length = 200).nullable()
    val movieId = reference(name = "movie_id", foreign = MovieTable)
}

class CoverEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    var backgroundImage by CoverTable.backgroundImage
    var foregroundImage by CoverTable.foregroundImage
    var movieId by CoverTable.movieId

    fun toDomain() = Cover(
        movieId = movieId.value,
        backgroundImage = backgroundImage,
        foregroundImage = foregroundImage
    )
    companion object : UUIDEntityClass<CoverEntity>(CoverTable)
}
