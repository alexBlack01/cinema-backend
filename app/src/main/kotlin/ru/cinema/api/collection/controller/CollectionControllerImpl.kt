package ru.cinema.api.collection.controller

import ru.cinema.api.collection.model.CollectionMovieBody
import ru.cinema.domain.collection.DeleteCollectionByUserUseCase
import ru.cinema.domain.collection.GetCollectionsByUserUseCase
import ru.cinema.domain.collection.PostCollectionByUserUseCase
import ru.cinema.domain.collection.model.Collection
import ru.cinema.domain.collectionmovie.DeleteMovieFromCollectionUseCase
import ru.cinema.domain.collectionmovie.GetMoviesByCollectionUseCase
import ru.cinema.domain.collectionmovie.PostMovieToCollectionUseCase
import ru.cinema.domain.collectionmovie.model.CollectionMovieForm
import ru.cinema.domain.movie.model.Movie
import java.util.*

class CollectionControllerImpl(
    private val getCollectionsByUserUseCase: GetCollectionsByUserUseCase,
    private val postCollectionByUserUseCase: PostCollectionByUserUseCase,
    private val deleteCollectionByUserUseCase: DeleteCollectionByUserUseCase,
    private val getMoviesByCollectionUseCase: GetMoviesByCollectionUseCase,
    private val postMovieToCollectionUseCase: PostMovieToCollectionUseCase,
    private val deleteMovieFromCollectionUseCase: DeleteMovieFromCollectionUseCase
) : CollectionController {

    override suspend fun getCollectionsByUser(userId: UUID): List<Collection> {
        return getCollectionsByUserUseCase(userId).getOrThrow()
    }

    override suspend fun postCollectionByUser(userId: UUID, collectionName: String): Collection {
        return postCollectionByUserUseCase(userId to collectionName).getOrThrow()
    }

    override suspend fun deleteCollectionByUser(userId: UUID, collectionId: UUID) {
        deleteCollectionByUserUseCase(userId to collectionId).getOrThrow()
    }

    override suspend fun getMoviesByCollection(userId: UUID, collectionId: UUID): List<Movie> {
        return getMoviesByCollectionUseCase(userId to collectionId).getOrThrow()
    }

    override suspend fun postMovieToCollection(
        userId: UUID,
        collectionId: UUID,
        collectionMovieBody: CollectionMovieBody
    ) {
        postMovieToCollectionUseCase(
            CollectionMovieForm(userId, collectionId, collectionMovieBody.movieId)
        ).getOrThrow()
    }

    override suspend fun deleteMovieFromCollection(userId: UUID, collectionId: UUID, movieId: UUID) {
        deleteMovieFromCollectionUseCase(CollectionMovieForm(userId, collectionId, movieId)).getOrThrow()
    }
}
