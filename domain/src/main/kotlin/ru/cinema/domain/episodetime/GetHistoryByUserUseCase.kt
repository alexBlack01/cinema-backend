package ru.cinema.domain.episodetime

import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.episode.EpisodeDbDataSource
import ru.cinema.domain.episode.model.ShortEpisodeInfo
import ru.cinema.domain.episodetime.model.EpisodeHistoryForm
import ru.cinema.domain.episodetime.model.EpisodeWithTime
import ru.cinema.domain.movie.MovieDbDataSource
import java.util.UUID

interface GetHistoryByUserUseCase : UseCase<UUID, List<EpisodeWithTime>>

class GetHistoryByUserUseCaseImpl(
    private val episodeTimeDbDataSource: EpisodeTimeDbDataSource,
    private val episodeDbDataSource: EpisodeDbDataSource,
    private val movieDbDataSource: MovieDbDataSource
) : GetHistoryByUserUseCase {

    /**
     * @param param is user id
     */
    override suspend fun execute(param: UUID): Result<List<EpisodeWithTime>> {
        val userHistoryWithoutEpisodeData = episodeTimeDbDataSource.getHistoryByUserUseCase(param)

        return successResult(mapEpisodeData(userHistoryWithoutEpisodeData))
    }

    private suspend fun mapEpisodeData(userHistoryWithoutEpisodeData: List<EpisodeHistoryForm>): List<EpisodeWithTime> {
        val listEpisodesWithTime: MutableList<EpisodeWithTime> = mutableListOf()
        var shortEpisodeInfo: ShortEpisodeInfo?

        val shortEpisodesInfo = episodeDbDataSource.getEpisodesById(
            episodeIds = userHistoryWithoutEpisodeData.map { it.episodeId }.distinct()
        )

        val shortMoviesInfo = movieDbDataSource.getMoviesById(
            movieIds = shortEpisodesInfo.map { it.movieId }.distinct()
        )

        userHistoryWithoutEpisodeData.forEach { item ->

            if (shortEpisodesInfo.any { it.episodeId == item.episodeId }) {
                shortEpisodeInfo = shortEpisodesInfo.find { it.episodeId == item.episodeId }

                listEpisodesWithTime.add(
                    EpisodeWithTime(
                        episodeId = item.episodeId,
                        movieId = shortEpisodeInfo?.movieId,
                        episodeName = shortEpisodeInfo?.episodeName,
                        movieName = shortMoviesInfo.find { it.movieId == shortEpisodeInfo?.movieId }?.name,
                        preview = shortEpisodeInfo?.preview,
                        filePath = shortEpisodeInfo?.filePath,
                        time = item.time
                    )
                )
            }
        }

        return listEpisodesWithTime
    }
}
