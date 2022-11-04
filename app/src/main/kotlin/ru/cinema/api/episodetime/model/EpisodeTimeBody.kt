package ru.cinema.api.episodetime.model

import io.konform.validation.Validation
import kotlinx.serialization.Serializable
import ru.cinema.api.common.request.ValidatedBody
import ru.cinema.api.common.validation.minValue
import ru.cinema.domain.episodetime.model.EpisodeTime
import java.util.*

@Serializable
data class EpisodeTimeBody(
    val timeInSeconds: Int
) : ValidatedBody<EpisodeTimeBody>() {
    override val validationRule: Validation<EpisodeTimeBody> = Validation {
        EpisodeTimeBody::timeInSeconds required {
            minValue(0)
        }
    }

    fun toDomain(userId: UUID, episodeId: UUID) = EpisodeTime(
        userId = userId,
        episodeId = episodeId,
        timeInSeconds = timeInSeconds
    )
}
