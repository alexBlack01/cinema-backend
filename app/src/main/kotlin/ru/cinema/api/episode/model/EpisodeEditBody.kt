package ru.cinema.api.episode.model

import io.konform.validation.Validation
import io.konform.validation.jsonschema.minLength
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.cinema.api.common.request.ValidatedBody
import ru.cinema.api.common.validation.isNotBlank
import ru.cinema.api.common.validation.isNotEmpty
import ru.cinema.api.common.validation.minValue
import ru.cinema.domain.episode.model.EditEpisodeForm
import java.util.*

@Serializable
data class EpisodeEditBody(
    val name: String? = null,
    val description: String? = null,
    val director: String? = null,
    val stars: List<@Contextual UUID>? = null,
    val year: Int? = null,
    val runtime: Int? = null,
    val filePath: String? = null
) : ValidatedBody<EpisodeEditBody>() {
    override val validationRule: Validation<EpisodeEditBody> = Validation {
        EpisodeEditBody::name ifPresent {
            minLength(length = 1)
            isNotBlank()
        }
        EpisodeEditBody::description ifPresent {
            minLength(length = 1)
            isNotBlank()
        }
        EpisodeEditBody::director ifPresent {
            minLength(length = 1)
            isNotBlank()
        }
        EpisodeEditBody::stars ifPresent {
            isNotEmpty()
        }
        EpisodeEditBody::year ifPresent {
            minValue(0)
        }
        EpisodeEditBody::runtime ifPresent {
            minValue(0)
        }
        EpisodeEditBody::filePath ifPresent {
            minLength(length = 1)
            isNotBlank()
        }
    }

    fun toDomain(episodeId: UUID) = EditEpisodeForm(
        episodeId = episodeId,
        name = name,
        description = description,
        director = director,
        stars = stars,
        year = year,
        runtime = runtime,
        filePath = filePath
    )
}
