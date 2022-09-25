package ru.cinema.data.db.movie.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import ru.cinema.data.db.episode.model.EpisodeEntity
import ru.cinema.data.db.episode.model.EpisodeTable
import ru.cinema.data.db.image.model.ImageEntity
import ru.cinema.data.db.image.model.ImageTable
import ru.cinema.data.db.tag.model.TagEntity
import ru.cinema.data.db.tagmovie.model.TagMovieTable
import ru.cinema.domain.movie.model.Movie
import java.util.*

object MovieTable : UUIDTable(name = "movies") {
    val name = varchar(name = "name", length = 150)
    val description = text(name = "description")
    val age = varchar(name = "age", length = 5)
    val poster = varchar(name = "poster", length = 200)
    val backgroundImage = varchar(name = "background_image", length = 200)
    val foregroundImage = varchar(name = "foreground_image", length = 200)
}

class MovieEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    var name by MovieTable.name
    var description by MovieTable.description
    var age by MovieTable.age
    var poster by MovieTable.poster
    var backgroundImage by MovieTable.backgroundImage
    var foregroundImage by MovieTable.foregroundImage

    val images by ImageEntity referrersOn ImageTable.movieId
    val tags by TagEntity via TagMovieTable
    val episodes by EpisodeEntity referrersOn EpisodeTable.movieId

    fun toDomain() = Movie(
        movieId = id.value,
        name = name,
        description = description,
        age = age,
        imageUrls = images.map { it.url },
        poster = poster,
        tags = tags.map { it.toDomain() },
        backgroundImage = backgroundImage,
        foregroundImage = foregroundImage
    )
    companion object : UUIDEntityClass<MovieEntity>(MovieTable)
}
