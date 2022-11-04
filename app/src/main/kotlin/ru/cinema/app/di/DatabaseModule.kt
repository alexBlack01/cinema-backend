package ru.cinema.app.di

import org.koin.dsl.module
import ru.cinema.app.common.utils.SystemEnvVariablesUtil
import ru.cinema.data.db.common.Database

object DatabaseModule {
    val module = module {
        single {
            Database.getHikariConfig(
                jdbcUrl = SystemEnvVariablesUtil.jdbcUrl,
                dbUser = SystemEnvVariablesUtil.databaseUser,
                dbPassword = SystemEnvVariablesUtil.databasePassword,
                poolSize = SystemEnvVariablesUtil.databaseConnectionCount
            )
        }
        single { Database.getHikariDataSource(config = get()) }
        single { Database.getDatabase(dataSource = get()) }
    }
}
