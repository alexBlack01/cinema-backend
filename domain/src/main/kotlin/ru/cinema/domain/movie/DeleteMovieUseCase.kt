package ru.cinema.domain.movie

import ru.cinema.domain.common.error.MovieNotFound
import ru.cinema.domain.common.usecase.UseCase
import java.util.UUID

interface DeleteMovieUseCase : UseCase<UUID, Unit>

class DeleteMovieUseCaseImpl(
    private val movieDbDataSource: MovieDbDataSource
) : DeleteMovieUseCase {

    /**
     * @param param is movie id
     */
    override suspend fun execute(param: UUID): Result<Unit> {
        movieDbDataSource.getMovieById(param) ?: throw MovieNotFound()

        return successResult(movieDbDataSource.deleteMovieById(param))
    }
}
