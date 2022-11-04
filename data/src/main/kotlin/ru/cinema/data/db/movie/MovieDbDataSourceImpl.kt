package ru.cinema.data.db.movie

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import ru.cinema.data.db.common.extensions.DatabaseDataSource
import ru.cinema.data.db.episode.model.EpisodeEntity
import ru.cinema.data.db.episode.model.EpisodeTable
import ru.cinema.data.db.image.model.ImageEntity
import ru.cinema.data.db.movie.model.MovieEntity
import ru.cinema.data.db.movie.model.MovieTable
import ru.cinema.data.db.tag.model.TagEntity
import ru.cinema.data.db.tagmovie.model.TagMovieTable
import ru.cinema.data.db.user.model.UserEntity
import ru.cinema.domain.cover.model.Cover
import ru.cinema.domain.episode.model.Episode
import ru.cinema.domain.movie.MovieDbDataSource
import ru.cinema.domain.movie.model.EditMovieForm
import ru.cinema.domain.movie.model.Movie
import ru.cinema.domain.movie.model.MovieForm
import ru.cinema.domain.movie.model.ShortMovieInfo
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.random.Random

class MovieDbDataSourceImpl(
    override val db: Database
) : MovieDbDataSource, DatabaseDataSource {

    override suspend fun getNewMovies(userId: UUID): List<Movie> = dbQuery {
        val userDislikes = UserEntity.findById(userId)?.dislikes?.map { it.id.value } ?: emptyList()

        val movies = MovieEntity.find { MovieTable.id notInList userDislikes }.map { it.toDomain() }

        movies.filter { movie ->
            ChronoUnit.DAYS.between(
                LocalDateTime.now(),
                movie.createdAt,
            ) <= DAYS_WHEN_MOVIE_IS_NEW }
    }

    override suspend fun getCompilationMovies(userId: UUID): List<Movie> = dbQuery {
        val userDislikes = UserEntity.findById(userId)?.dislikes?.map { it.id.value } ?: emptyList()

        val movies = MovieEntity.find { MovieTable.id notInList userDislikes }.map { it.toDomain() }

        if (movies.count() > COUNT_OF_RAND_MOVIE) {
            getRandomListMovie(movies)
        } else {
            movies
        }
    }

    override suspend fun getPersonalMovies(userId: UUID): List<Movie> = dbQuery {
        val userPreferences = UserEntity.findById(userId)?.tags?.map { it.id.value } ?: emptyList()
        val userDislikes = UserEntity.findById(userId)?.dislikes?.map { it.id.value } ?: emptyList()

        val moviesByPreferences = MovieEntity.find {
            MovieTable.id eq TagMovieTable.movieId and (TagMovieTable.tagId inList userPreferences)
        }.map { it.id.value }

        MovieEntity.find { MovieTable.id notInList userDislikes and (MovieTable.id inList moviesByPreferences) }
            .map { it.toDomain() }
    }

    override suspend fun getLastViewMovies(userId: UUID): List<Movie> = dbQuery {
        val episodeIdLastView = UserEntity.findById(userId)?.histories?.map { it.episodeId.value } ?: emptyList()
        val movieIdLastView = EpisodeEntity.find { EpisodeTable.id inList episodeIdLastView }
            .map { it.movieId.value }.distinct()

        MovieEntity.find { MovieTable.id inList movieIdLastView }.map { it.toDomain() }
    }

    override suspend fun getTrendMovies(userId: UUID): List<Movie> = dbQuery {
        val userDislikes = UserEntity.findById(userId)?.dislikes?.map { it.id.value } ?: emptyList()

        MovieEntity.find { MovieTable.id notInList userDislikes }
            .orderBy(MovieTable.viewCount to SortOrder.ASC)
            .limit(n = 5, offset = 10).map { it.toDomain() }
    }

    override suspend fun getAllMovies(): List<Movie> = dbQuery {
        getAll()
    }

    override suspend fun getMovieById(movieId: UUID): Movie? = dbQuery {
        MovieEntity.findById(movieId)?.toDomain()
    }

    override suspend fun getBestMovieByViewCount(): Cover? = dbQuery {
        MovieEntity.all()
            .orderBy(MovieTable.viewCount to SortOrder.ASC)
            .firstOrNull()?.toDomainForCover()
    }

    override suspend fun insertNewMovie(movieData: MovieForm): Movie = dbQuery {
        val movie = MovieEntity.new {
            this.name = movieData.name
            this.description = movieData.description
            this.age = movieData.age
            this.createdAt = movieData.createdAt.toInstant(ZoneOffset.UTC)
        }

        movieData.tags.forEach { tagId ->
            if (TagEntity.findById(tagId)?.id != null) {
                TagMovieTable.insert {
                    it[this.movieId] = movie.id.value
                    it[this.tagId] = tagId
                }
            }
        }

        movie.toDomain()
    }

    override suspend fun insertMovieImages(
        movieId: UUID,
        posterName: String?,
        images: List<String>,
        backgroundImage: String?,
        foregroundImage: String?
    ) = dbQueryWithoutResult {
        val movie = MovieEntity.findById(movieId)

        movie?.poster = posterName
        movie?.backgroundImage = backgroundImage
        movie?.foregroundImage = foregroundImage

        images.forEach { imageName ->
            ImageEntity.new {
                this.url = imageName
                this.movieId = MovieEntity[movieId].id
            }
        }
    }

    override suspend fun editMovie(editMovieForm: EditMovieForm): Movie? = dbQuery {
        val movie = MovieEntity.findById(editMovieForm.movieId)?.apply {
            editMovieForm.name?.let { name ->
                this.name = name
            }
            editMovieForm.description?.let { description ->
                this.description = description
            }
            editMovieForm.age?.let { age ->
                this.age = age
            }
            editMovieForm.createdAt?.let { createdAt ->
                this.createdAt = createdAt.toInstant(ZoneOffset.UTC)
            }
        }

        editMovieForm.tags?.let { tags ->
            TagMovieTable.deleteWhere {
                TagMovieTable.movieId eq editMovieForm.movieId and (TagMovieTable.tagId notInList tags)
            }

            tags.forEach { tagId ->
                if (!TagMovieTable.select(TagMovieTable.tagId eq tagId).any()) {
                    TagMovieTable.insert {
                        it[this.movieId] = editMovieForm.movieId
                        it[this.tagId] = tagId
                    }
                }
            }
        }

        movie?.toDomain()
    }

    override suspend fun deleteMovieById(movieId: UUID) = dbQueryWithoutResult {
        MovieTable.deleteWhere { MovieTable.id eq movieId }
    }

    override suspend fun getEpisodesByMovie(movieId: UUID): List<Episode> = dbQuery {
        MovieEntity.findById(movieId)?.episodes
            ?.orderBy(EpisodeTable.name to SortOrder.ASC)
            ?.map { it.toDomain() }.orEmpty()
    }

    override suspend fun getMoviesById(movieIds: List<UUID>): List<ShortMovieInfo> = dbQuery {
        MovieEntity.find { MovieTable.id inList movieIds }.map { it.toDomainShortMovieInfo() }
    }

    private fun getAll(): List<Movie> = MovieEntity
        .all()
        .orderBy(MovieTable.name to SortOrder.ASC)
        .map { it.toDomain() }

    private fun getRandomListMovie(movies: List<Movie>): List<Movie> {
        val listMovies: MutableList<Movie> = mutableListOf()

        do {
            val randValue = Random.nextInt(START_VALUE, movies.count())

            if (!listMovies.contains(movies[randValue])) {
                listMovies.add(movies[randValue])
            }
        } while (listMovies.count() != COUNT_OF_RAND_MOVIE)

        return listMovies
    }

    private companion object {
        const val DAYS_WHEN_MOVIE_IS_NEW = 7
        const val COUNT_OF_RAND_MOVIE = 10
        const val START_VALUE = 0
    }
}
