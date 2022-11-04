package ru.cinema.api.episode.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.cinema.domain.common.error.InvalidFileName
import ru.cinema.domain.episode.model.EpisodeContentType

@Serializable
enum class ContentTypeDto {
    @SerialName("preview")
    PREVIEW,

    @SerialName("images")
    IMAGES,

    @SerialName("file")
    FILE;

    fun toDomain(): EpisodeContentType = when (this) {
        PREVIEW -> EpisodeContentType.PREVIEW
        IMAGES -> EpisodeContentType.IMAGES
        FILE -> EpisodeContentType.FILE
    }
}

fun String.toValidateEnumType(): ContentTypeDto {
    return when (this) {
        "preview" -> ContentTypeDto.PREVIEW
        "images" -> ContentTypeDto.IMAGES
        "file" -> ContentTypeDto.FILE
        else -> throw InvalidFileName()
    }
}
