package ru.cinema.data.db.tag.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import ru.cinema.data.db.movie.model.MovieEntity
import ru.cinema.data.db.tagmovie.model.TagMovieTable
import ru.cinema.data.db.user.model.UserEntity
import ru.cinema.data.db.usertag.model.UserTagTable
import ru.cinema.domain.tag.model.Tag
import java.util.*

object TagTable : UUIDTable(name = "tags") {
    val tagName = varchar(name = "tag_name", length = 150)
    val categoryName = varchar(name = "category_name", length = 150)
}

class TagEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    var tagName by TagTable.tagName
    var categoryName by TagTable.categoryName

    val movies by MovieEntity via TagMovieTable
    val users by UserEntity via UserTagTable

    fun toDomain() = Tag(
        id = id.value,
        tagName = tagName,
        categoryName = categoryName
    )
    companion object : UUIDEntityClass<TagEntity>(TagTable)
}
