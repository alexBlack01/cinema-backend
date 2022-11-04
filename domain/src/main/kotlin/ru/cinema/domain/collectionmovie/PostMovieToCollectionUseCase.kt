package ru.cinema.domain.collectionmovie

import ru.cinema.domain.collection.CollectionDbDataSource
import ru.cinema.domain.collectionmovie.model.CollectionMovieForm
import ru.cinema.domain.common.error.PermissionDenied
import ru.cinema.domain.common.usecase.UseCase

interface PostMovieToCollectionUseCase : UseCase<CollectionMovieForm, Unit>

class PostMovieToCollectionUseCaseImpl(
    private val collectionMovieDbDataSource: CollectionMovieDbDataSource,
    private val collectionDbDataSource: CollectionDbDataSource
) : PostMovieToCollectionUseCase {

    override suspend fun execute(param: CollectionMovieForm): Result<Unit> {
        if (!collectionDbDataSource.isUserCollection(param.userId, param.collectionId)) throw PermissionDenied()

        return successResult(collectionMovieDbDataSource.postMovieToCollection(param.collectionId, param.movieId))
    }
}
