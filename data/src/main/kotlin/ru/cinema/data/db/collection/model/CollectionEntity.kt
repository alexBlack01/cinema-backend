package ru.cinema.data.db.collection.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import ru.cinema.data.db.collectionmovie.model.CollectionMovieTable
import ru.cinema.data.db.movie.model.MovieEntity
import ru.cinema.data.db.user.model.UserEntity
import ru.cinema.data.db.user.model.UserTable
import ru.cinema.domain.collection.model.Collection
import java.util.*

object CollectionTable : UUIDTable(name = "collections") {
    val name = varchar(name = "name", length = 300)
    val userId = reference(name = "user_id", foreign = UserTable)
}

class CollectionEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    var name by CollectionTable.name
    var userId by CollectionTable.userId

    val user by UserEntity referencedOn CollectionTable.userId
    val movies by MovieEntity via CollectionMovieTable

    fun toDomain() = Collection(
        collectionId = id.value,
        name = name
    )
    companion object : UUIDEntityClass<CollectionEntity>(CollectionTable)
}
