package ru.cinema.domain.episode

import ru.cinema.domain.common.error.EpisodeNotFound
import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.common.utils.ImageUtils
import ru.cinema.domain.episode.model.EpisodeContentForm
import ru.cinema.domain.image.ImageLocalDataSource
import ru.cinema.domain.movie.model.ContentForm

interface PutEpisodeFilesUseCase : UseCase<EpisodeContentForm, Unit>

class PutEpisodeFilesUseCaseImpl(
    private val episodeDbDataSource: EpisodeDbDataSource,
    private val imageLocalDataSource: ImageLocalDataSource
) : PutEpisodeFilesUseCase {

    override suspend fun execute(param: EpisodeContentForm): Result<Unit> {
        episodeDbDataSource.getEpisodeById(param.episodeId) ?: throw EpisodeNotFound()

        return successResult(saveFilesInDbSource(param))
    }

    private suspend fun saveFilesInDbSource(episodeFiles: EpisodeContentForm) = with(episodeFiles) {
        val imageNames = mutableListOf<String>()

        val previewName: String? = preview?.let { saveFilesInLocalDataSource(it) }
        images.forEach { image ->
            imageNames.add(
                saveFilesInLocalDataSource(image)
            )
        }
        val fileName: String? = filePath?.let { saveFilesInLocalDataSource(it) }

        episodeDbDataSource.insertEpisodeFiles(episodeId, imageNames, previewName, fileName)
    }

    private suspend fun saveFilesInLocalDataSource(content: ContentForm): String {
        val name = ImageUtils.generateFileName(content.fileType)

        imageLocalDataSource.saveImage(content.fileBytes, content.filePath, name)

        return name
    }
}
