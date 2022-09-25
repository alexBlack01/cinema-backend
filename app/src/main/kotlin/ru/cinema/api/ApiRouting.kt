package ru.cinema.api

import io.ktor.server.routing.Routing
import ru.cinema.api.auth.configureAuthRouting
import ru.cinema.api.episodecomment.configureCommentsRouting
import ru.cinema.api.movie.configureMoviesRouting
import ru.cinema.api.tag.configureTagsRouting
import ru.cinema.api.user.configureUserRouting

fun Routing.configureApiRouting() {
    configureAuthRouting()
    configureTagsRouting()
    configureMoviesRouting()
    configureCommentsRouting()
    configureUserRouting()
}
