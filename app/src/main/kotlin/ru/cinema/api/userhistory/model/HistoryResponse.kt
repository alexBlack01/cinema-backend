package ru.cinema.api.userhistory.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.cinema.api.common.extensions.toResourceUrl
import ru.cinema.domain.episodetime.model.EpisodeWithTime
import java.util.*

@Serializable
data class HistoryResponse(
    @Contextual
    val episodeId: UUID,
    @Contextual
    val movieId: UUID?,
    val episodeName: String?,
    val movieName: String?,
    val preview: String?,
    val filePath: String?,
    val time: Int
) {

    companion object {
        fun fromDomain(
            data: EpisodeWithTime,
            baseUrl: String,
            uploadFolder: String,
            previewFolder: String,
            fileFolder: String
        ) = HistoryResponse(
            episodeId = data.episodeId,
            movieId = data.movieId,
            episodeName = data.episodeName,
            movieName = data.movieName,
            preview = data.preview?.toResourceUrl(baseUrl, uploadFolder, previewFolder),
            filePath = data.filePath?.toResourceUrl(baseUrl, uploadFolder, fileFolder),
            time = data.time
        )
    }
}
