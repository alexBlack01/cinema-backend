package ru.cinema.api.episode.controller

import io.ktor.http.content.MultiPartData
import ru.cinema.api.episode.model.EpisodeBody
import ru.cinema.api.episode.model.EpisodeEditBody
import ru.cinema.domain.episode.model.Episode
import java.util.*

interface EpisodeController {

    suspend fun getEpisodesByMovie(movieId: UUID): List<Episode>

    suspend fun postNewEpisode(movieId: UUID, episodeData: EpisodeBody): Episode

    suspend fun insertFilesForEpisode(movieId: UUID, episodeId: UUID, files: MultiPartData)

    suspend fun patchEpisodeById(movieId: UUID, episodeId: UUID, editEpisodeBody: EpisodeEditBody): Episode

    suspend fun deleteEpisodeById(movieId: UUID, episodeId: UUID)
}
