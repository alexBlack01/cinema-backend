package ru.cinema.api.movie.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.cinema.domain.movie.model.MovieType

@Serializable
enum class MovieTypeDto {
    @SerialName("new")
    NEW,

    @SerialName("inTrend")
    IN_TREND,

    @SerialName("forMe")
    FOR_ME,

    @SerialName("lastView")
    LAST_VIEW,

    @SerialName("compilation")
    COMPILATION;

    fun toDomain(): MovieType = when (this) {
        NEW -> MovieType.NEW
        IN_TREND -> MovieType.IN_TREND
        FOR_ME -> MovieType.FOR_ME
        LAST_VIEW -> MovieType.LAST_VIEW
        COMPILATION -> MovieType.COMPILATION
    }
}
