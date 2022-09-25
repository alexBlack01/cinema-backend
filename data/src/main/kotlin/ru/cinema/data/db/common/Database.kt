package ru.cinema.data.db.common

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

object Database {
    fun getHikariConfig(jdbcUrl: String, dbUser: String, dbPassword: String, poolSize: Int) = HikariConfig().apply {
        this.jdbcUrl = jdbcUrl
        this.username = dbUser
        this.password = dbPassword
        driverClassName = "org.postgresql.Driver"
        isAutoCommit = true
        maximumPoolSize = poolSize
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }

    fun getHikariDataSource(config: HikariConfig): HikariDataSource = HikariDataSource(config)

    fun getDatabase(dataSource: HikariDataSource) = Database.connect(dataSource)

    fun runMigrations(dataSource: DataSource) {
        Flyway.configure().dataSource(dataSource).load().migrate()
    }
}
