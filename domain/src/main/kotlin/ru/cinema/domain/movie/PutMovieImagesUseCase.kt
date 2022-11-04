package ru.cinema.domain.movie

import ru.cinema.domain.common.error.MovieNotFound
import ru.cinema.domain.common.usecase.UseCase
import ru.cinema.domain.common.utils.ImageUtils
import ru.cinema.domain.image.ImageLocalDataSource
import ru.cinema.domain.movie.model.ContentForm
import ru.cinema.domain.movie.model.MovieContentForm

interface PutMovieImagesUseCase : UseCase<MovieContentForm, Unit>

class PutMovieImagesUseCaseImpl(
    private val movieDbDataSource: MovieDbDataSource,
    private val imageLocalDataSource: ImageLocalDataSource
) : PutMovieImagesUseCase {

    override suspend fun execute(param: MovieContentForm): Result<Unit> {
        movieDbDataSource.getMovieById(param.movieId) ?: throw MovieNotFound()

        return successResult(saveImagesInDbSource(param))
    }

    private suspend fun saveImagesInDbSource(movieImages: MovieContentForm) = with(movieImages) {
        val imageNames = mutableListOf<String>()

        val posterName: String? = poster?.let { saveImageInLocalDataSource(it) }
        images.forEach { image ->
            imageNames.add(
                saveImageInLocalDataSource(image)
            )
        }
        val backgroundName: String? = backgroundImage?.let { saveImageInLocalDataSource(it) }
        val foregroundName: String? = foregroundImage?.let { saveImageInLocalDataSource(it) }

        movieDbDataSource.insertMovieImages(movieId, posterName, imageNames, backgroundName, foregroundName)
    }

    private suspend fun saveImageInLocalDataSource(content: ContentForm): String {
        val name = ImageUtils.generateFileName(content.fileType)

        imageLocalDataSource.saveImage(content.fileBytes, content.filePath, name)

        return name
    }
}
