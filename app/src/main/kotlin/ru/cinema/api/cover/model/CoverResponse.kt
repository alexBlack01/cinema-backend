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
        fun fromDomain(data: Cover) = CoverResponse(
            backgroundImage = data.backgroundImage?.toResourceUrl(),
            foregroundImage = data.foregroundImage?.toResourceUrl()
        )
    }
}
