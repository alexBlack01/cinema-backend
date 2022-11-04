package ru.cinema.domain.common.scheduler.task

import ru.cinema.domain.auth.token.AuthTokenDbDataSource
import ru.cinema.domain.common.utils.DateTimeUtils

class DbCleaningTask(
    override val period: ScheduleTaskPeriod,
    private val authTokenDbDataSource: AuthTokenDbDataSource
) : ScheduledTask {

    override suspend fun execute() {
        val time = DateTimeUtils.getNowSeconds()
        authTokenDbDataSource.deleteTokensByTime(time)
    }
}
