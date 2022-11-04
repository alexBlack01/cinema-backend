package ru.cinema.api.movie.model

import io.konform.validation.Validation
import io.konform.validation.jsonschema.minLength
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.cinema.api.common.request.ValidatedBody
import ru.cinema.api.common.validation.isNotBlank
import ru.cinema.api.common.validation.isNotEmpty
import ru.cinema.domain.movie.model.EditMovieForm
import java.time.LocalDateTime
import java.util.*

@Serializable
data class MovieEditBody(
    val name: String? = null,
    val description: String? = null,
    val age: String? = null,
    val tags: List<@Contextual UUID>? = null,
    @Contextual
    val createdAt: LocalDateTime? = null
) : ValidatedBody<MovieEditBody>() {
    override val validationRule: Validation<MovieEditBody> = Validation {
        MovieEditBody::name ifPresent {
            minLength(length = 1)
            isNotBlank()
        }
        MovieEditBody::description ifPresent {
            minLength(length = 1)
            isNotBlank()
        }
        MovieEditBody::age ifPresent {
            minLength(length = 1)
            isNotBlank()
        }
        MovieEditBody::tags ifPresent {
            isNotEmpty()
        }
    }

    fun toDomain(movieId: UUID) = EditMovieForm(
        movieId = movieId,
        name = name,
        description = description,
        age = age,
        tags = tags,
        createdAt = createdAt
    )
}
