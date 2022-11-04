package ru.cinema.api.common.extensions

import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.user(block: Route.() -> Unit) {
    block.invoke(this)
}

fun Route.admin(block: Route.() -> Unit) {
    route("admin") {
        block.invoke(this)
    }
}
