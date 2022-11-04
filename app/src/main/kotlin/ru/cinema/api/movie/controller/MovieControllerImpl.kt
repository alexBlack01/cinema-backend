package ru.cinema.api.movie.controller

import io.ktor.http.content.MultiPartData
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import ru.cinema.api.common.utils.ContentUtils
import ru.cinema.api.common.utils.FileUrlUtils
import ru.cinema.api.common.validation.isValidateImage
import ru.cinema.api.movie.model.MovieBody
import ru.cinema.api.movie.model.MovieEditBody
import ru.cinema.api.movie.model.request.ContentTypeDto
import ru.cinema.api.movie.model.request.toValidateEnumType
import ru.cinema.domain.common.error.InvalidFileName
import ru.cinema.domain.dislike.PostDislikeByUserUseCase
import ru.cinema.domain.movie.DeleteMovieUseCase
import ru.cinema.domain.movie.GetAllMoviesUseCase
import ru.cinema.domain.movie.PatchMovieUseCase
import ru.cinema.domain.movie.PostMovieUseCase
import ru.cinema.domain.movie.PutMovieImagesUseCase
import ru.cinema.domain.movie.model.ContentForm
import ru.cinema.domain.movie.model.Movie
import ru.cinema.domain.movie.model.MovieContentForm
import ru.cinema.domain.movie.model.MovieContentType
import ru.cinema.domain.movie.model.MovieType
import java.util.*

@Suppress("LongParameterList")
class MovieControllerImpl(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val postMovieUseCase: PostMovieUseCase,
    private val putMovieImagesUseCase: PutMovieImagesUseCase,
    private val patchMovieUseCase: PatchMovieUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val postDislikeByUserUseCase: PostDislikeByUserUseCase
) : MovieController {

    override suspend fun getAllMovies(userId: UUID, movieType: MovieType): List<Movie> {
        return getAllMoviesUseCase(userId to movieType).getOrThrow()
    }

    override suspend fun postNewMovie(movieData: MovieBody): Movie {
        return postMovieUseCase(movieData.toDomain()).getOrThrow()
    }

    override suspend fun insertImagesForMovie(movieId: UUID, images: MultiPartData) {
        var posterBytes: ContentForm? = null
        val imagesBytes = mutableListOf<ContentForm>()
        var backgroundImage: ContentForm? = null
        var foregroundImage: ContentForm? = null

        images.forEachPart { part ->
            if (part is PartData.FileItem) {
                when (part.name?.toValidateEnumType()) {
                    ContentTypeDto.POSTER -> {
                        posterBytes =
                            ContentUtils.createContentForm(
                                part,
                                FileUrlUtils.getUrlForMovieContent(MovieContentType.POSTER)
                            ).apply { isValidateImage() }
                    }

                    ContentTypeDto.IMAGES -> {
                        imagesBytes.add(
                            ContentUtils.createContentForm(
                                part,
                                FileUrlUtils.getUrlForMovieContent(MovieContentType.IMAGES)
                            ).apply { isValidateImage() }
                        )
                    }

                    ContentTypeDto.BACKGROUND_IMAGE -> {
                        backgroundImage = ContentUtils.createContentForm(
                            part,
                            FileUrlUtils.getUrlForMovieContent(MovieContentType.BACKGROUND_IMAGE)
                        ).apply { isValidateImage() }
                    }

                    ContentTypeDto.FOREGROUND_IMAGE -> {
                        foregroundImage = ContentUtils.createContentForm(
                            part,
                            FileUrlUtils.getUrlForMovieContent(MovieContentType.FOREGROUND_IMAGE)
                        ).apply { isValidateImage() }
                    }

                    else -> throw InvalidFileName()
                }
            }
        }

        putMovieImagesUseCase(
            MovieContentForm(
                movieId = movieId,
                poster = posterBytes,
                images = imagesBytes,
                backgroundImage = backgroundImage,
                foregroundImage = foregroundImage
            )
        ).getOrThrow()
    }

    override suspend fun patchMovieById(movieId: UUID, editMovieBody: MovieEditBody): Movie {
        return patchMovieUseCase(editMovieBody.toDomain(movieId)).getOrThrow()
    }

    override suspend fun deleteMovieById(movieId: UUID) {
        deleteMovieUseCase(movieId).getOrThrow()
    }

    override suspend fun postDislikeByUser(userId: UUID, movieId: UUID) {
        postDislikeByUserUseCase(userId to movieId).getOrThrow()
    }
}
