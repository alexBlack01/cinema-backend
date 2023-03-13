package ru.cinema.api.collection

import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import ru.cinema.api.collection.controller.CollectionController
import ru.cinema.api.collection.model.CollectionBody
import ru.cinema.api.collection.model.CollectionMovieBody
import ru.cinema.api.collection.model.CollectionResponse
import ru.cinema.api.collection.route.Collection
import ru.cinema.api.collection.route.CollectionMovie
import ru.cinema.api.collection.route.DeleteCollection
import ru.cinema.api.collection.route.DeleteCollectionMovie
import ru.cinema.api.common.extensions.principalOrThrow
import ru.cinema.api.common.extensions.respondSuccess
import ru.cinema.api.common.extensions.respondSuccessCreated
import ru.cinema.api.common.extensions.respondSuccessNoContent
import ru.cinema.api.movie.model.MovieResponse
import ru.cinema.auth.model.AuthConstants
import ru.cinema.auth.model.UserPrincipal

fun Route.configureCollectionsRouting() {
    val controller by inject<CollectionController>()

    authenticate(AuthConstants.USER_AUTH) {
        get<Collection> {
            val userId = call.principalOrThrow<UserPrincipal>().userId

            call.respondSuccess(
                controller.getCollectionsByUser(userId).map {
                    CollectionResponse.fromDomain(it)
                }
            )
        }

        post<Collection> {
            val userId = call.principalOrThrow<UserPrincipal>().userId

            val collectionName = call.receive<CollectionBody>()

            val collection = controller.postCollectionByUser(userId, collectionName.name)
            call.respondSuccess(
                CollectionResponse.fromDomain(collection)
            )
        }

        delete<DeleteCollection> { param ->
            val userId = call.principalOrThrow<UserPrincipal>().userId

            controller.deleteCollectionByUser(userId, param.collectionId)

            call.respondSuccessNoContent()
        }

        get<CollectionMovie> { param ->
            val userId = call.principalOrThrow<UserPrincipal>().userId

            call.respondSuccess(
                controller.getMoviesByCollection(userId, param.collectionId).map {
                    MovieResponse.fromDomain(it)
                }
            )
        }

        post<CollectionMovie> { param ->
            val userId = call.principalOrThrow<UserPrincipal>().userId

            val movieId = call.receive<CollectionMovieBody>()

            controller.postMovieToCollection(userId, param.collectionId, movieId)
            call.respondSuccessCreated()
        }

        delete<DeleteCollectionMovie> { param ->
            val userId = call.principalOrThrow<UserPrincipal>().userId

            controller.deleteMovieFromCollection(userId, param.collectionId, param.movieId)
            call.respondSuccessNoContent()
        }
    }
}
