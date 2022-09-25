package ru.cinema.domain.common.extensions

import java.util.*

fun String.toUUID(): UUID = UUID.fromString(this)

@Suppress("SwallowedException")
fun String.toUUIDOrNull() = try {
    this.toUUID()
} catch (e: IllegalArgumentException) {
    null
}
