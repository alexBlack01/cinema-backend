package ru.cinema.api.movie

import io.ktor.server.application.application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.resources.get
import io.ktor.server.routing.Routing
import org.koin.ktor.ext.inject
import ru.cinema.api.common.extensions.getBaseUrl
import ru.cinema.api.common.extensions.respondSuccess
import ru.cinema.api.movie.controller.MovieController
import ru.cinema.api.movie.model.EpisodeResponse
import ru.cinema.api.movie.model.MovieResponse
import ru.cinema.api.movie.route.Episodes
import ru.cinema.api.movie.route.Movies

fun Routing.configureMoviesRouting() {
    val controller by inject<MovieController>()

    authenticate {
        get<Movies> { params ->
            call.respondSuccess(
                controller.getAllMovies(
                    params.movieType.toDomain()
                ).map { MovieResponse.fromDomain(data = it, baseUrl = application.getBaseUrl()) }
            )
        }

        get<Episodes> { params ->
            call.respondSuccess(
                controller.getEpisodesByMovie(
                    params.movieId
                ).map { EpisodeResponse.fromDomain(data = it, baseUrl = application.getBaseUrl()) }
            )
        }
    }
}
