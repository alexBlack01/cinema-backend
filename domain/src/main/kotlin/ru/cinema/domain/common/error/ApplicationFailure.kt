@file:Suppress("UnnecessaryAbstractClass")

package ru.cinema.domain.common.error

abstract class ApplicationFailure : Throwable() {
    open val code: String = this::class.java.simpleName
}

// region NotFound errors

abstract class NotFoundFailure : ApplicationFailure()

class UserNotFound(override val message: String? = null) : NotFoundFailure()

class CoverNotFound(override val message: String? = null) : NotFoundFailure()

class MovieNotFound(override val message: String? = null) : NotFoundFailure()

class TagNotFound(override val message: String? = null) : NotFoundFailure()

class CommentNotFound(override val message: String? = null) : NotFoundFailure()

class EpisodeNotFound(override val message: String? = null) : NotFoundFailure()

// endregion

// region Forbidden errors

abstract class ForbiddenFailure : ApplicationFailure()

class NoNecessaryRole(override val message: String? = null) : ForbiddenFailure()

class PermissionDenied(override val message: String? = null) : ForbiddenFailure()

// endregion

// region Conflict errors

abstract class ConflictFailure : ApplicationFailure()

class UserAlreadyExists(override val message: String? = null) : ConflictFailure()

class TagAlreadyExists(override val message: String? = null) : ConflictFailure()

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

// region File errors

abstract class FileFailure : ApplicationFailure()

class InvalidFileType(override val message: String? = null) : FileFailure()

class InvalidFileSize(override val message: String? = null) : FileFailure()

class InvalidFileName(override val message: String? = null) : FileFailure()

// endregion

// region Chat errors

abstract class ChatFailure : ApplicationFailure()

class ChatNotFound(override val message: String? = null) : ChatFailure()

class InvalidChatId(override val message: String? = null) : ChatFailure()

class MemberAlreadyExists(override val message: String? = null) : ChatFailure()

// endregion
