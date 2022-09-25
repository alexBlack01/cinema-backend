package ru.cinema.data.db.image.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import ru.cinema.data.db.movie.model.MovieEntity
import ru.cinema.data.db.movie.model.MovieTable
import ru.cinema.domain.image.model.Image
import java.util.*

object ImageTable : UUIDTable(name = "images") {
    val url = varchar(name = "url", length = 150)
    val movieId = reference(name = "movie_id", foreign = MovieTable, onDelete = ReferenceOption.CASCADE)
}

class ImageEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    var url by ImageTable.url

    var movie by MovieEntity referencedOn ImageTable.movieId

    fun toDomain() = Image(
        id = id.value,
        url = url,
    )

    companion object : UUIDEntityClass<ImageEntity>(ImageTable)
}
