package ru.cinema.app.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.cinema.app.common.utils.SystemEnvVariablesUtil
import ru.cinema.domain.common.scheduler.Scheduler
import ru.cinema.domain.common.scheduler.SchedulerImpl
import ru.cinema.domain.common.scheduler.task.CoverTask
import ru.cinema.domain.common.scheduler.task.DbCleaningTask
import ru.cinema.domain.common.scheduler.task.ScheduleTaskPeriod
import ru.cinema.domain.common.scheduler.task.ScheduledTask
import java.util.concurrent.Executors

object ScheduledModule {
    private const val SCHEDULED_TASK_SCOPE = "SCHEDULED_TASK_SCOPE"

    private const val COVER_SCHEDULED_TASK = "COVER_SCHEDULED_TASK"
    private const val DB_CLEANING_SCHEDULED_TASK = "DB_CLEANING_SCHEDULED_TASK"

    val module = module {
        single(named(SCHEDULED_TASK_SCOPE)) {
            CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher() + SupervisorJob())
        }
        single<Scheduler> {
            SchedulerImpl(
                initTasks = getAll(),
                scope = get(named(SCHEDULED_TASK_SCOPE))
            )
        }
        single<ScheduledTask>(named(COVER_SCHEDULED_TASK)) {
            CoverTask(
                period = ScheduleTaskPeriod.Daily(
                    launchTimeInSeconds = SystemEnvVariablesUtil.coverLaunchInSeconds,
                    startNow = true,
                    periodValue = 1 // Once per day
                ),
                movieDbDataSource = get(),
                coverDbDataSource = get()
            )
        }
        single<ScheduledTask>(named(DB_CLEANING_SCHEDULED_TASK)) {
            DbCleaningTask(
                period = ScheduleTaskPeriod.Daily(
                    launchTimeInSeconds = SystemEnvVariablesUtil.refreshTokenValidity,
                    startNow = true,
                    periodValue = 1 // Once per day
                ),
                authTokenDbDataSource = get()
            )
        }
    }
}
