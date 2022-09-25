@file:Suppress("UnnecessaryAbstractClass")

package ru.cinema.domain.common.error

abstract class ApplicationFailure : Throwable() {
    open val code: String = this::class.java.simpleName
}

// region NotFound errors

abstract class NotFoundFailure : ApplicationFailure()

class UserNotFound(override val message: String? = null) : NotFoundFailure()

// endregion

// region Forbidden errors

abstract class ForbiddenFailure : ApplicationFailure()

class NoNecessaryRole(override val message: String? = null) : ForbiddenFailure()

// endregion

// region Conflict errors

abstract class ConflictFailure : ApplicationFailure()

class UserAlreadyExists(override val message: String? = null) : ConflictFailure()

// endregion

// region Unauthorized errors

abstract class UnauthorizedFailure : ApplicationFailure()

class InvalidAccessToken(override val message: String? = null) : UnauthorizedFailure()

class InvalidUserCredentials(override val message: String? = null) : UnauthorizedFailure()

class AccessTokenExpired(override val message: String? = null) : UnauthorizedFailure()

class RefreshTokenExpired(override val message: String? = null) : UnauthorizedFailure()

class InvalidRefreshToken(override val message: String? = null) : UnauthorizedFailure()

// endregion

// region InternalServer errors

abstract class InternalServerFailure : ApplicationFailure()

// endregion
