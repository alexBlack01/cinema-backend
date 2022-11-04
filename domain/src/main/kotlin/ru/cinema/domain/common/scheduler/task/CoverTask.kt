package ru.cinema.domain.common.scheduler.task

import ru.cinema.domain.cover.CoverDbDataSource
import ru.cinema.domain.movie.MovieDbDataSource

class CoverTask(
    override val period: ScheduleTaskPeriod,
    private val movieDbDataSource: MovieDbDataSource,
    private val coverDbDataSource: CoverDbDataSource
) : ScheduledTask {

    override suspend fun execute() {
        val cover = movieDbDataSource.getBestMovieByViewCount()

        if (cover != null) {
            coverDbDataSource.updateCover(cover)
        }
    }
}
