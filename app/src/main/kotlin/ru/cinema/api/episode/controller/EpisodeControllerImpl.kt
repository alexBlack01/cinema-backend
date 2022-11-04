package ru.cinema.api.episode.controller

import io.ktor.http.content.MultiPartData
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import ru.cinema.api.common.utils.ContentUtils
import ru.cinema.api.common.utils.FileUrlUtils
import ru.cinema.api.common.validation.isValidateImage
import ru.cinema.api.common.validation.isValidateVideo
import ru.cinema.api.episode.model.EpisodeBody
import ru.cinema.api.episode.model.EpisodeEditBody
import ru.cinema.api.episode.model.request.ContentTypeDto
import ru.cinema.api.episode.model.request.toValidateEnumType
import ru.cinema.domain.common.error.InvalidFileName
import ru.cinema.domain.episode.DeleteEpisodeUseCase
import ru.cinema.domain.episode.GetEpisodesByMovieUseCase
import ru.cinema.domain.episode.PatchEpisodeUseCase
import ru.cinema.domain.episode.PostEpisodeByMovieUseCase
import ru.cinema.domain.episode.PutEpisodeFilesUseCase
import ru.cinema.domain.episode.model.Episode
import ru.cinema.domain.episode.model.EpisodeContentForm
import ru.cinema.domain.episode.model.EpisodeContentType
import ru.cinema.domain.movie.model.ContentForm
import java.util.*

class EpisodeControllerImpl(
    private val getEpisodesByMovieUseCase: GetEpisodesByMovieUseCase,
    private val postEpisodeByMovieUseCase: PostEpisodeByMovieUseCase,
    private val putEpisodeFilesUseCase: PutEpisodeFilesUseCase,
    private val patchEpisodeUseCase: PatchEpisodeUseCase,
    private val deleteEpisodeUseCase: DeleteEpisodeUseCase
) : EpisodeController {

    override suspend fun getEpisodesByMovie(movieId: UUID): List<Episode> {
        return getEpisodesByMovieUseCase(movieId).getOrThrow()
    }

    override suspend fun postNewEpisode(movieId: UUID, episodeData: EpisodeBody): Episode {
        return postEpisodeByMovieUseCase(episodeData.toDomain(movieId)).getOrThrow()
    }

    override suspend fun insertFilesForEpisode(movieId: UUID, episodeId: UUID, files: MultiPartData) {
        var previewBytes: ContentForm? = null
        val imagesBytes = mutableListOf<ContentForm>()
        var file: ContentForm? = null

        files.forEachPart { part ->
            if (part is PartData.FileItem) {
                when (part.name?.toValidateEnumType()) {
                    ContentTypeDto.PREVIEW -> {
                        previewBytes =
                            ContentUtils.createContentForm(
                                part,
                                FileUrlUtils.getUrlForEpisodeContent(EpisodeContentType.PREVIEW)
                            ).apply { isValidateImage() }
                    }

                    ContentTypeDto.IMAGES -> {
                        imagesBytes.add(
                            ContentUtils.createContentForm(
                                part,
                                FileUrlUtils.getUrlForEpisodeContent(EpisodeContentType.IMAGES)
                            ).apply { isValidateImage() }
                        )
                    }

                    ContentTypeDto.FILE -> {
                        file = ContentUtils.createContentForm(
                            part,
                            FileUrlUtils.getUrlForEpisodeContent(EpisodeContentType.FILE)
                        ).apply { isValidateVideo() }
                    }

                    else -> throw InvalidFileName()
                }
            }
        }

        putEpisodeFilesUseCase(
            EpisodeContentForm(
                episodeId = episodeId,
                preview = previewBytes,
                images = imagesBytes,
                filePath = file
            )
        ).getOrThrow()
    }

    override suspend fun patchEpisodeById(movieId: UUID, episodeId: UUID, editEpisodeBody: EpisodeEditBody): Episode {
        return patchEpisodeUseCase(editEpisodeBody.toDomain(episodeId)).getOrThrow()
    }

    override suspend fun deleteEpisodeById(movieId: UUID, episodeId: UUID) {
        deleteEpisodeUseCase(movieId to episodeId).getOrThrow()
    }
}
