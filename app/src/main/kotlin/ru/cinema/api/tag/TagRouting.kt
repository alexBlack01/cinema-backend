package ru.cinema.api.tag

import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.resources.get
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject
import ru.cinema.api.common.extensions.respondSuccess
import ru.cinema.api.tag.controller.TagControllerImpl
import ru.cinema.api.tag.model.TagResponse
import ru.cinema.api.tag.route.Tags

fun Route.configureTagsRouting() {
    val controller by inject<TagControllerImpl>()

    authenticate {
        get<Tags> {
            call.respondSuccess(controller.getAllTags().map { TagResponse.fromDomain(it) })
        }
    }
}
