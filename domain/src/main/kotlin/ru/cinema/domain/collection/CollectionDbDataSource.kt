package ru.cinema.domain.collection

import ru.cinema.domain.collection.model.Collection
import java.util.UUID

interface CollectionDbDataSource {

    suspend fun getCollectionsByUser(userId: UUID): List<Collection>

    suspend fun postCollectionByUser(userId: UUID, collectionName: String): Collection

    suspend fun isUserCollection(userId: UUID, collectionId: UUID): Boolean

    suspend fun deleteCollectionByUser(collectionId: UUID)
}
