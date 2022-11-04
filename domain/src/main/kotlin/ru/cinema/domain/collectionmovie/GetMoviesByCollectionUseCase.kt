package ru.cinema.domain.collectionmovie

import ru.cinema.domain.collection.CollectionDbDataSource
import ru.cinema.domain.common.error.PermissionDenied
import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.movie.model.Movie
import java.util.UUID

interface GetMoviesByCollectionUseCase : UseCase<Pair<UUID, UUID>, List<Movie>>

class GetMoviesByCollectionUseCaseImpl(
    private val collectionMovieDbDataSource: CollectionMovieDbDataSource,
    private val collectionDbDataSource: CollectionDbDataSource
) : GetMoviesByCollectionUseCase {

    /**
     * @param param.first is user id
     * @param param.second is collection id
     */
    override suspend fun execute(param: Pair<UUID, UUID>): Result<List<Movie>> {
        if (!collectionDbDataSource.isUserCollection(param.first, param.second)) throw PermissionDenied()

        return successResult(collectionMovieDbDataSource.getMoviesByCollection(param.second))
    }
}
