package ru.cinema.app.di

object KoinModules {
    val all = listOf(
        DatabaseModule.module,
        DataSourceModule.module,
        UseCaseModule.module,
        AuthModule.module,
        ControllerModule.module,
        ScheduledModule.module
    )
}
