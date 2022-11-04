package ru.cinema.data.db.collection

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.deleteWhere
import ru.cinema.data.db.collection.model.CollectionEntity
import ru.cinema.data.db.collection.model.CollectionTable
import ru.cinema.data.db.common.extensions.DatabaseDataSource
import ru.cinema.data.db.user.model.UserEntity
import ru.cinema.domain.collection.CollectionDbDataSource
import ru.cinema.domain.collection.model.Collection
import java.util.*

class CollectionDbDataSourceImpl(
    override val db: Database
) : CollectionDbDataSource, DatabaseDataSource {

    override suspend fun getCollectionsByUser(userId: UUID): List<Collection> = dbQuery {
        getAllByUser(userId)
    }

    override suspend fun isUserCollection(userId: UUID, collectionId: UUID): Boolean = dbQuery {
        getAllByUser(userId).any { it.collectionId == collectionId }
    }

    override suspend fun postCollectionByUser(userId: UUID, collectionName: String): Collection = dbQuery {
        val collection = CollectionEntity.new {
            this.userId = UserEntity[userId].id
            this.name = collectionName
        }

        collection.toDomain()
    }

    override suspend fun deleteCollectionByUser(collectionId: UUID) = dbQueryWithoutResult {
        CollectionTable.deleteWhere { CollectionTable.id eq collectionId }
    }

    private fun getAllByUser(userId: UUID): List<Collection> = UserEntity
        .findById(userId)?.collections
        ?.orderBy(CollectionTable.name to SortOrder.ASC)
        ?.map { it.toDomain() } ?: emptyList()
}
