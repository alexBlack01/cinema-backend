package ru.cinema.app.common.utils

object SystemEnvVariablesUtil {

    private const val KEY_HOST = "APPLICATION_HOST"

    val host: String get() = System.getenv(KEY_HOST).trim(CHAR_URL_SLASH)

    // region Jwt

    private const val KEY_ACCESS_TOKEN_VALIDITY = "ACCESS_TOKEN_VALIDITY"
    private const val KEY_REFRESH_TOKEN_VALIDITY = "REFRESH_TOKEN_VALIDITY"

    private const val KEY_JWT_USER_SECRET = "JWT_USER_SECRET"
    private const val KEY_JWT_USER_REFRESH_SECRET = "JWT_USER_REFRESH_SECRET"
    private const val KEY_JWT_ADMIN_SECRET = "JWT_ADMIN_SECRET"
    private const val KEY_JWT_ADMIN_REFRESH_SECRET = "JWT_ADMIN_REFRESH_SECRET"

    val jwtUserSecret: String get() = System.getenv(KEY_JWT_USER_SECRET)
    val jwtUserRefreshSecret: String get() = System.getenv(KEY_JWT_USER_REFRESH_SECRET)
    val jwtAdminSecret: String get() = System.getenv(KEY_JWT_ADMIN_SECRET)
    val jwtAdminRefreshSecret: String get() = System.getenv(KEY_JWT_ADMIN_REFRESH_SECRET)

    val accessTokenValidity: Long get() = System.getenv(KEY_ACCESS_TOKEN_VALIDITY).toLong()
    val refreshTokenValidity: Long get() = System.getenv(KEY_REFRESH_TOKEN_VALIDITY).toLong()

    // endregion

    // region Database

    private const val KEY_JDBC_URL = "JDBC_URL"
    private const val KEY_DATABASE_USER = "DATABASE_USER"
    private const val KEY_DATABASE_PASSWORD = "DATABASE_PASSWORD"
    private const val KEY_DATABASE_CONNECTION_COUNT = "DATABASE_CONNECTION_COUNT"

    val jdbcUrl: String get() = System.getenv(KEY_JDBC_URL)
    val databaseUser: String get() = System.getenv(KEY_DATABASE_USER)
    val databasePassword: String get() = System.getenv(KEY_DATABASE_PASSWORD)
    val databaseConnectionCount: Int get() = System.getenv(KEY_DATABASE_CONNECTION_COUNT).toInt()

    // endregion

    // region Scheduled Jobs

    private const val KEY_COVER_LAUNCH_IN_SECONDS = "COVER_LAUNCH_IN_SECONDS"

    val coverLaunchInSeconds: Long get() = System.getenv(KEY_COVER_LAUNCH_IN_SECONDS).toLong()

    // endregion

    // region Upload Files

    private const val KEY_UPLOAD_FOLDER = "UPLOAD_FOLDER"
    private const val KEY_AVATAR_FOLDER = "AVATAR_FOLDER"
    private const val KEY_POSTER_FOLDER = "POSTER_FOLDER"
    private const val KEY_MOVIE_IMAGE_FOLDER = "MOVIE_IMAGE_FOLDER"
    private const val KEY_BACKGROUND_IMAGE_FOLDER = "BACKGROUND_IMAGE_FOLDER"
    private const val KEY_FOREGROUND_IMAGE_FOLDER = "FOREGROUND_IMAGE_FOLDER"
    private const val KEY_EPISODE_IMAGE_FOLDER = "EPISODE_IMAGE_FOLDER"
    private const val KEY_PREVIEW_FOLDER = "PREVIEW_FOLDER"
    private const val KEY_FILE_FOLDER = "FILE_FOLDER"

    val uploadFolder: String get() = System.getenv(KEY_UPLOAD_FOLDER)
    val avatarFolder: String get() = System.getenv(KEY_AVATAR_FOLDER)
    val posterFolder: String get() = System.getenv(KEY_POSTER_FOLDER)
    val movieImageFolder: String get() = System.getenv(KEY_MOVIE_IMAGE_FOLDER)
    val backgroundImageFolder: String get() = System.getenv(KEY_BACKGROUND_IMAGE_FOLDER)
    val foregroundImageFolder: String get() = System.getenv(KEY_FOREGROUND_IMAGE_FOLDER)
    val episodeImageFolder: String get() = System.getenv(KEY_EPISODE_IMAGE_FOLDER)
    val previewFolder: String get() = System.getenv(KEY_PREVIEW_FOLDER)
    val fileFolder: String get() = System.getenv(KEY_FILE_FOLDER)

    // endregion

    private const val CHAR_URL_SLASH = '/'
}
