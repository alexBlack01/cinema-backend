package ru.cinema.api.collection.route

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import ru.cinema.api.common.serializations.UUIDSerializer
import java.util.*

@Serializable
@Resource("collections")
class Collection

@Serializable
@Resource("collections/{collectionId}")
class DeleteCollection(@Serializable(with = UUIDSerializer::class) val collectionId: UUID)

@Serializable
@Resource("collections/{collectionId}/movies")
class CollectionMovie(@Serializable(with = UUIDSerializer::class) val collectionId: UUID)

@Serializable
@Resource("collections/{collectionId}/movies")
class DeleteCollectionMovie(
    @Serializable(with = UUIDSerializer::class) val collectionId: UUID,
    @Serializable(with = UUIDSerializer::class) val movieId: UUID
)
