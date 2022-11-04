package ru.cinema.api.movie.model

import io.konform.validation.Validation
import io.konform.validation.jsonschema.minLength
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.cinema.api.common.request.ValidatedBody
import ru.cinema.api.common.validation.isNotBlank
import ru.cinema.api.common.validation.isNotEmpty
import ru.cinema.domain.movie.model.MovieForm
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class MovieBody(
    val name: String,
    val description: String,
    val age: String,
    val tags: List<@Contextual UUID>,
    @Contextual
    val createdAt: LocalDateTime
) : ValidatedBody<MovieBody>() {
    override val validationRule: Validation<MovieBody> = Validation {
        MovieBody::name required {
            minLength(length = 1)
            isNotBlank()
        }
        MovieBody::description required {
            minLength(length = 1)
            isNotBlank()
        }
        MovieBody::age required {
            minLength(length = 1)
            isNotBlank()
        }
        MovieBody::tags required {
            isNotEmpty()
        }
    }

    fun toDomain() = MovieForm(
        name = name,
        description = description,
        age = age,
        tags = tags,
        createdAt = createdAt

    )
}
