package ru.cinema.api.episode.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.cinema.api.common.extensions.toResourceUrl
import ru.cinema.domain.episode.model.Episode
import java.util.*

@Serializable
data class EpisodeResponse(
    @Contextual
    val episodeId: UUID,
    val name: String,
    val description: String,
    val director: String,
    val stars: List<String>,
    val year: Int,
    val images: List<String>,
    val runtime: Int,
    val preview: String?,
    val filePath: String?
) {

    @Suppress("LongParameterList")
    companion object {
        fun fromDomain(
            data: Episode,
            baseUrl: String,
            uploadFolder: String,
            episodeImageFolder: String,
            previewFolder: String,
            fileFolder: String
        ) = EpisodeResponse(
            episodeId = data.episodeId,
            name = data.name,
            description = data.description,
            director = data.director,
            stars = data.stars,
            year = data.year,
            images = data.imageUrls.map { it.toResourceUrl(baseUrl, uploadFolder, episodeImageFolder) },
            runtime = data.runtime,
            preview = data.preview?.toResourceUrl(baseUrl, uploadFolder, previewFolder),
            filePath = data.filePath?.toResourceUrl(baseUrl, uploadFolder, fileFolder)
        )
    }
}
