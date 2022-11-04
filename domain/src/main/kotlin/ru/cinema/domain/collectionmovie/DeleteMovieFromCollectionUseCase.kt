package ru.cinema.domain.collectionmovie

import ru.cinema.domain.collection.CollectionDbDataSource
import ru.cinema.domain.collectionmovie.model.CollectionMovieForm
import ru.cinema.domain.common.error.PermissionDenied
import ru.cinema.domain.common.usecase.UseCase

interface DeleteMovieFromCollectionUseCase : UseCase<CollectionMovieForm, Unit>

class DeleteMovieFromCollectionUseCaseImpl(
    private val collectionMovieDbDataSource: CollectionMovieDbDataSource,
    private val collectionDbDataSource: CollectionDbDataSource
) : DeleteMovieFromCollectionUseCase {

    override suspend fun execute(param: CollectionMovieForm): Result<Unit> {
        if (!collectionDbDataSource.isUserCollection(param.userId, param.collectionId)) throw PermissionDenied()

        return successResult(collectionMovieDbDataSource.deleteMovieFromCollection(param.collectionId, param.movieId))
    }
}
