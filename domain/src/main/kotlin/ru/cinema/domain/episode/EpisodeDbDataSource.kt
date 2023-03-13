package ru.cinema.domain.episode

import ru.cinema.domain.episode.model.EditEpisodeForm
import ru.cinema.domain.episode.model.Episode
import ru.cinema.domain.episode.model.EpisodeForm
import ru.cinema.domain.episode.model.ShortEpisodeInfo
import java.util.UUID

interface EpisodeDbDataSource {

    suspend fun getEpisodesById(episodeIds: List<UUID>): List<ShortEpisodeInfo>
    suspend fun getEpisodeById(episodeId: UUID): ShortEpisodeInfo?
    suspend fun insertNewEpisode(episodeData: EpisodeForm): Episode
    suspend fun insertEpisodeFiles(
        episodeId: UUID,
        images: List<String>,
        preview: String?
    )
    suspend fun editEpisode(editEpisodeForm: EditEpisodeForm): Episode?
    suspend fun deleteEpisodeById(movieId: UUID, episodeId: UUID)
}
