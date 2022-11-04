package ru.cinema.domain.common.scheduler.task

import ru.cinema.domain.common.utils.DateTimeUtils
import java.time.ZoneOffset

sealed class ScheduleTaskPeriod {
    abstract val startNow: Boolean
    abstract val periodValue: Long
    abstract val launchTimeInSeconds: Long

    abstract fun getNextExecutionMillis(): Long

    data class Daily(
        override val launchTimeInSeconds: Long,
        override val startNow: Boolean = false,
        override val periodValue: Long = 1
    ) : ScheduleTaskPeriod() {

        override fun getNextExecutionMillis(): Long = DateTimeUtils
            .getTodayStartOfDay()
            .plusDays(periodValue)
            .plusSeconds(launchTimeInSeconds)
            .toInstant(ZoneOffset.UTC)
            .toEpochMilli()
    }
}
