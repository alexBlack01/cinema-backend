package ru.cinema.api.collection.controller

import ru.cinema.api.collection.model.CollectionMovieBody
import ru.cinema.domain.collection.model.Collection
import ru.cinema.domain.movie.model.Movie
import java.util.*

interface CollectionController {

    suspend fun getCollectionsByUser(userId: UUID): List<Collection>

    suspend fun postCollectionByUser(userId: UUID, collectionName: String): Collection

    suspend fun deleteCollectionByUser(userId: UUID, collectionId: UUID)

    suspend fun getMoviesByCollection(userId: UUID, collectionId: UUID): List<Movie>

    suspend fun postMovieToCollection(userId: UUID, collectionId: UUID, collectionMovieBody: CollectionMovieBody)

    suspend fun deleteMovieFromCollection(userId: UUID, collectionId: UUID, movieId: UUID)
}
