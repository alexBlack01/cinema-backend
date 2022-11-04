package ru.cinema.api.episode.model

import io.konform.validation.Validation
import io.konform.validation.jsonschema.minLength
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.cinema.api.common.request.ValidatedBody
import ru.cinema.api.common.validation.isNotBlank
import ru.cinema.api.common.validation.isNotEmpty
import ru.cinema.api.common.validation.minValue
import ru.cinema.domain.episode.model.EpisodeForm
import java.util.*

@Serializable
data class EpisodeBody(
    val name: String,
    val description: String,
    val director: String,
    val stars: List<@Contextual UUID>,
    val year: Int,
    val runtime: Int
) : ValidatedBody<EpisodeBody>() {
    override val validationRule: Validation<EpisodeBody> = Validation {
        EpisodeBody::name required {
            minLength(length = 1)
            isNotBlank()
        }
        EpisodeBody::description required {
            minLength(length = 1)
            isNotBlank()
        }
        EpisodeBody::director required {
            minLength(length = 1)
            isNotBlank()
        }
        EpisodeBody::stars required {
            isNotEmpty()
        }
        EpisodeBody::year required {
            minValue(0)
        }
        EpisodeBody::runtime required {
            minValue(0)
        }
    }

    fun toDomain(movieId: UUID) = EpisodeForm(
        movieId = movieId,
        name = name,
        description = description,
        director = director,
        stars = stars,
        year = year,
        runtime = runtime
    )
}
