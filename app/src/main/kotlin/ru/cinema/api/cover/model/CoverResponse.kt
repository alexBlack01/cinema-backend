package ru.cinema.api.cover.model

import kotlinx.serialization.Serializable
import ru.cinema.api.common.extensions.toResourceUrl
import ru.cinema.domain.cover.model.Cover

@Serializable
data class CoverResponse(
    val backgroundImage: String?,
    val foregroundImage: String?
) {

    companion object {
        fun fromDomain(
            data: Cover,
            baseUrl: String,
            uploadFolder: String,
            backgroundImageFolder: String,
            foregroundImageFolder: String
        ) = CoverResponse(
            backgroundImage = data.backgroundImage?.toResourceUrl(baseUrl, uploadFolder, backgroundImageFolder),
            foregroundImage = data.foregroundImage?.toResourceUrl(baseUrl, uploadFolder, foregroundImageFolder)
        )
    }
}
