package ru.cinema.domain.episodetime

import ru.cinema.domain.common.usecase.UseCase
import java.util.UUID

interface GetEpisodeTimeByUserUseCase : UseCase<Pair<UUID, UUID>, Int?>

class GetEpisodeTimeByUserUseCaseImpl(
    private val episodeTimeDbDataSource: EpisodeTimeDbDataSource
) : GetEpisodeTimeByUserUseCase {

    /**
     * @param param.first is user id
     * @param param.second is episode id
     */
    override suspend fun execute(param: Pair<UUID, UUID>): Result<Int?> {
        return successResult(episodeTimeDbDataSource.getEpisodeTimeByUser(param.first, param.second))
    }
}
