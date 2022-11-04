package ru.cinema.api.common.utils

import ru.cinema.app.common.utils.SystemEnvVariablesUtil
import ru.cinema.domain.episode.model.EpisodeContentType
import ru.cinema.domain.movie.model.MovieContentType

object FileUrlUtils {

    fun getUrlForMovieContent(movieContentType: MovieContentType) =
        "${SystemEnvVariablesUtil.uploadFolder}/${
        when (movieContentType) {
            MovieContentType.POSTER -> SystemEnvVariablesUtil.posterFolder
            MovieContentType.IMAGES -> SystemEnvVariablesUtil.movieImageFolder
            MovieContentType.BACKGROUND_IMAGE -> SystemEnvVariablesUtil.backgroundImageFolder
            MovieContentType.FOREGROUND_IMAGE -> SystemEnvVariablesUtil.foregroundImageFolder
        }
        }"

    fun getUrlForEpisodeContent(movieContentType: EpisodeContentType) =
        "${SystemEnvVariablesUtil.uploadFolder}/${
        when (movieContentType) {
            EpisodeContentType.PREVIEW -> SystemEnvVariablesUtil.previewFolder
            EpisodeContentType.IMAGES -> SystemEnvVariablesUtil.episodeImageFolder
            EpisodeContentType.FILE -> SystemEnvVariablesUtil.fileFolder
        }
        }"

    fun getUrlForAvatarContent() = "${SystemEnvVariablesUtil.uploadFolder}/${SystemEnvVariablesUtil.avatarFolder}"
}
