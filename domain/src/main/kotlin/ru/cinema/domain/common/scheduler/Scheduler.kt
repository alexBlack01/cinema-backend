package ru.cinema.domain.common.scheduler

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.cinema.domain.common.DomainConstants
import ru.cinema.domain.common.scheduler.task.ScheduledTask
import ru.cinema.domain.common.utils.DateTimeUtils
import java.time.Instant
import kotlin.math.max
import kotlin.random.Random

abstract class Scheduler {
    protected val tasks = sortedMapOf<Long, ScheduledTask>()

    abstract fun start()
    abstract fun stop()
    abstract fun addTask(vararg tasksWithTime: Pair<Long, ScheduledTask>)
    abstract fun addTask(tasksWithTime: List<Pair<Long, ScheduledTask>>)
    protected abstract fun schedule()
}

class SchedulerImpl(
    private val initTasks: List<ScheduledTask>,
    private val scope: CoroutineScope
) : Scheduler() {
    private var schedulerJob: Job? = null
    private var currentDelayMillis: Long = 0
        set(value) {
            field = value
            schedule()
        }

    override fun start() {
        handleInitTasks()
    }

    override fun stop() {
        schedulerJob?.cancel()
        scope.cancel()
    }

    override fun schedule() {
        schedulerJob?.cancel()
        schedulerJob = scope.launch(Dispatchers.IO) {
            delay(currentDelayMillis)
            val firstKey = tasks.firstKey() ?: kotlin.run {
                recalculateDelay()
                return@launch
            }
            val task = tasks.remove(firstKey) ?: kotlin.run {
                recalculateDelay()
                return@launch
            }

            executeTask(task)

            val nextTaskTime = task.period.getNextExecutionMillis()
            tasks[nextTaskTime] = task
            recalculateDelay()
        }
    }

    override fun addTask(vararg tasksWithTime: Pair<Long, ScheduledTask>) {
        addTask(tasksWithTime.toList())
    }

    override fun addTask(tasksWithTime: List<Pair<Long, ScheduledTask>>) {
        tasksWithTime.forEach { pair ->
            tasks[pair.first] = pair.second
        }
        recalculateDelay()
    }

    private fun executeTask(task: ScheduledTask) {
        scope.launch(Dispatchers.IO) {
            task.execute()
        }
    }

    private fun recalculateDelay() {
        currentDelayMillis = max(0, tasks.firstKey() - Instant.now().toEpochMilli())
    }

    private fun handleInitTasks() {
        val tasksToAdd = initTasks.map { task ->
            val taskLaunchTime = DateTimeUtils.getNowMillis() +
                Random.nextInt(DEFAULT_NOW_LAUNCH_OFFSET_IN_SECONDS) * DomainConstants.SECOND_IN_MILLIS
            val startTime = if (task.period.startNow) {
                taskLaunchTime
            } else {
                DateTimeUtils.addSecondsToStartOfDayInMillis(task.period.launchTimeInSeconds)
            }

            startTime to task
        }
        addTask(tasksToAdd)
    }

    private companion object {
        const val DEFAULT_NOW_LAUNCH_OFFSET_IN_SECONDS = 30
    }
}
