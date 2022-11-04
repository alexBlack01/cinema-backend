package ru.cinema.domain.common.scheduler.task

interface ScheduledTask {
    val period: ScheduleTaskPeriod

    suspend fun execute()
}
