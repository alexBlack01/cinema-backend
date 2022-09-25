package ru.cinema.api.common.error

import ru.cinema.domain.common.error.ApplicationFailure
import ru.cinema.domain.common.error.UnauthorizedFailure

data class BadRequestFailure(override val message: String? = null) : ApplicationFailure()

class InvalidCredentials : UnauthorizedFailure()
