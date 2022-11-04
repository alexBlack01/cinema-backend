package ru.cinema.domain.episodetime

import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.episodetime.model.EpisodeTime

interface PostEpisodeTimeByUserUseCase : UseCase<EpisodeTime, Unit>

class PostEpisodeTimeByUserUseCaseImpl(
    private val episodeTimeDbDataSource: EpisodeTimeDbDataSource
) : PostEpisodeTimeByUserUseCase {

    override suspend fun execute(param: EpisodeTime): Result<Unit> {
        return successResult(episodeTimeDbDataSource.postEpisodeTimeByUser(param))
    }
}
