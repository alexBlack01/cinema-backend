# Cinema Backend

### System Env Variables:

```kotlin

// Database
JDBC_URL = "Url for connect to database"
DATABASE_USER = "Database user login"
DATABASE_PASSWORD = "Database user password"
DATABASE_CONNECTION_COUNT = "Database connection pool size"

```

## Tech

Cinema API uses a number of open source projects to work properly:

- [Ktor](https://ktor.io/) - a framework to easily build connected applications – web applications, HTTP services,
  mobile and browser applications.
- [PostgreSQL](https://www.postgresql.org/) - a powerful, open source object-relational database system with over 30
  years of active development that has earned it a strong reputation for reliability, feature robustness, and
  performance.
- [Flyway](https://flywaydb.org/) - a tool for rolling migrations.
- [Exposed](https://github.com/JetBrains/Exposed) - an open-source library (Apache license), which provides an idiomatic
  Kotlin API for some relational database implementations while smoothing out the differences among database vendors.

## Architecture

- Clean architecture