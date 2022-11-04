package ru.cinema.app.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStopping
import org.koin.ktor.ext.inject
import ru.cinema.domain.common.scheduler.Scheduler

fun Application.configureScheduledJobs() {
    val scheduler by inject<Scheduler>()

    scheduler.start()
    environment.monitor.subscribe(ApplicationStopping) {
        scheduler.stop()
    }
}
