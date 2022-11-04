package ru.cinema.domain.episode

import ru.cinema.domain.common.error.EpisodeNotFound
import ru.cinema.domain.common.usecase.UseCase
import java.util.UUID

interface DeleteEpisodeUseCase : UseCase<Pair<UUID, UUID>, Unit>

class DeleteEpisodeUseCaseImpl(
    private val episodeDbDataSource: EpisodeDbDataSource
) : DeleteEpisodeUseCase {

    /**
     * @param param.first is movie id
     * @param param.second is episode id
     */
    override suspend fun execute(param: Pair<UUID, UUID>): Result<Unit> {
        episodeDbDataSource.getEpisodeById(param.second) ?: throw EpisodeNotFound()

        return successResult(episodeDbDataSource.deleteEpisodeById(param.first, param.second))
    }
}
