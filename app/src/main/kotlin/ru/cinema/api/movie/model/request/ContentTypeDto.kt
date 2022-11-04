package ru.cinema.api.movie.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.cinema.domain.common.error.InvalidFileName
import ru.cinema.domain.movie.model.MovieContentType

@Serializable
enum class ContentTypeDto {
    @SerialName("poster")
    POSTER,

    @SerialName("images")
    IMAGES,

    @SerialName("backgroundImage")
    BACKGROUND_IMAGE,

    @SerialName("foregroundImage")
    FOREGROUND_IMAGE;

    fun toDomain(): MovieContentType = when (this) {
        POSTER -> MovieContentType.POSTER
        IMAGES -> MovieContentType.IMAGES
        BACKGROUND_IMAGE -> MovieContentType.BACKGROUND_IMAGE
        FOREGROUND_IMAGE -> MovieContentType.FOREGROUND_IMAGE
    }
}

fun String.toValidateEnumType(): ContentTypeDto {
    return when (this) {
        "poster" -> ContentTypeDto.POSTER
        "images" -> ContentTypeDto.IMAGES
        "backgroundImage" -> ContentTypeDto.BACKGROUND_IMAGE
        "foregroundImage" -> ContentTypeDto.FOREGROUND_IMAGE
        else -> throw InvalidFileName()
    }
}
