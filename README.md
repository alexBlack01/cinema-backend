# Cinema Backend

### System Env Variables:

```kotlin

// Common
APPLICATION_HOST = "Url to server"

// Database
JDBC_URL = "Url for connect to database"
DATABASE_USER = "Database user login"
DATABASE_PASSWORD = "Database user password"
DATABASE_CONNECTION_COUNT = "Database connection pool size"

// JWT
ACCESS_TOKEN_VALIDITY = "Duration in seconds when the access token is valid"
REFRESH_TOKEN_VALIDITY = "Duration in seconds when the refresh token is valid"
JWT_USER_SECRET = "Secret for user jwt generator"
JWT_ADMIN_SECRET = "Secret for admin panel jwt generator"
JWT_ADMIN_REFRESH_SECRET = "Secret for admin panel refresh jwt generator"
JWT_USER_REFRESH_SECRET = "Secret for user refresh jwt generator"

// FOLDERS
UPLOAD_FOLDER = "Base folder to store media files"
AVATAR_FOLDER = "Folder to store user avatar"
BACKGROUND_IMAGE_FOLDER = "Folder to store movie background image"
FOREGROUND_IMAGE_FOLDER = "Folder to store movie foreground image"
EPISODE_IMAGE_FOLDER = "Folder to store episode images"
FILE_FOLDER = "Folder to store episode video file"
MOVIE_IMAGE_FOLDER = "Folder to store movie images"
POSTER_FOLDER = "Folder to store movie poster"
PREVIEW_FOLDER = "Folder to store episode preview"

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