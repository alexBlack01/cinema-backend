package ru.cinema.api.movie

import io.ktor.http.content.MultiPartData
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.request.receiveMultipart
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.patch
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import ru.cinema.api.common.extensions.admin
import ru.cinema.api.common.extensions.principalOrThrow
import ru.cinema.api.common.extensions.respondSuccess
import ru.cinema.api.common.extensions.respondSuccessCreated
import ru.cinema.api.common.extensions.respondSuccessNoContent
import ru.cinema.api.common.extensions.user
import ru.cinema.api.common.routing.RoutingRoleVerifier.verifyRoles
import ru.cinema.api.movie.controller.MovieController
import ru.cinema.api.movie.model.MovieBody
import ru.cinema.api.movie.model.MovieEditBody
import ru.cinema.api.movie.model.MovieResponse
import ru.cinema.api.movie.route.AllMovies
import ru.cinema.api.movie.route.MovieImage
import ru.cinema.api.movie.route.UpdateMovie
import ru.cinema.api.movie.route.Movies
import ru.cinema.api.movie.route.Dislike
import ru.cinema.api.movie.route.NewMovie
import ru.cinema.auth.model.AuthConstants
import ru.cinema.auth.model.UserPrincipal
import ru.cinema.domain.movie.model.Movie
import ru.cinema.domain.movie.model.MovieType
import ru.cinema.domain.user.roles.model.UserRole
import java.util.UUID

fun Route.configureMoviesRouting() {
    val controller by inject<MovieController>()

    movieUser(
        getMoviesByFilter = controller::getMoviesByFilter,
        postDislike = controller::postDislikeByUser
    )
    movieAdmin(
        getAllMovies = controller::getAllMovies,
        createMovieRequest = controller::postNewMovie,
        putMovieContentRequest = controller::insertImagesForMovie,
        patchMovieInfoRequest = controller::patchMovieById,
        deleteMovieRequest = controller::deleteMovieById
    )
}

private fun Route.movieUser(
    getMoviesByFilter: suspend (UUID, MovieType) -> List<Movie>,
    postDislike: suspend (UUID, UUID) -> Unit
) = user {
    authenticate(AuthConstants.USER_AUTH) {
        get<Movies> { params ->
            val userId = call.principalOrThrow<UserPrincipal>().userId

            call.respondSuccess(
                getMoviesByFilter.invoke(
                    userId,
                    params.movieType.toDomain()
                ).map {
                    MovieResponse.fromDomain(it)
                }
            )
        }

        post<Dislike> { param ->
            val userId = call.principalOrThrow<UserPrincipal>().userId

            postDislike.invoke(userId, param.movieId)
            call.respondSuccessCreated()
        }
    }
}

private fun Route.movieAdmin(
    getAllMovies: suspend () -> List<Movie>,
    createMovieRequest: suspend (MovieBody) -> Movie,
    putMovieContentRequest: suspend (UUID, MultiPartData) -> Unit,
    patchMovieInfoRequest: suspend (UUID, MovieEditBody) -> Movie,
    deleteMovieRequest: suspend (UUID) -> Unit
) = admin {
    authenticate(AuthConstants.ADMIN_AUTH) {
        get<AllMovies> {
            call.respondSuccess(
                getAllMovies.invoke().map {
                    MovieResponse.fromDomain(it)
                }
            )
        }

        post<NewMovie> {
            verifyRoles(setOf(UserRole.ADMIN))
            val body = call.receive<MovieBody>()

            val movie = createMovieRequest.invoke(body)
            call.respondSuccess(
                MovieResponse.fromDomain(movie)
            )
        }

        put<MovieImage> { param ->
            verifyRoles(setOf(UserRole.ADMIN))

            val images = call.receiveMultipart()
            putMovieContentRequest.invoke(param.movieId, images)
            call.respondSuccessNoContent()
        }

        patch<UpdateMovie> { param ->
            verifyRoles(setOf(UserRole.ADMIN))
            val body = call.receive<MovieEditBody>()

            val movie = patchMovieInfoRequest.invoke(param.movieId, body)
            call.respondSuccess(
                MovieResponse.fromDomain(movie)
            )
        }

        delete<UpdateMovie> { param ->
            verifyRoles(setOf(UserRole.ADMIN))

            deleteMovieRequest.invoke(param.movieId)
            call.respondSuccessNoContent()
        }
    }
}
