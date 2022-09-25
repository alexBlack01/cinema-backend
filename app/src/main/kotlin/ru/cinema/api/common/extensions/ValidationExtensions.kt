package ru.cinema.api.common.extensions

import io.konform.validation.Invalid
import io.konform.validation.Validation
import io.ktor.server.plugins.BadRequestException

fun <T> Validation<T>.validateAndThrow(value: T) {
    val result = this.validate(value)
    if (result is Invalid<T>) throw BadRequestException(message = result.errors.joinToString())
}
