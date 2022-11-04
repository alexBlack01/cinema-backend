package ru.cinema.api.episodetime.model

import kotlinx.serialization.Serializable

@Serializable
data class EpisodeTimeResponse(
    val timeInSeconds: Int?
) {
    companion object {
        fun fromDomain(timeInSeconds: Int?) = EpisodeTimeResponse(
            timeInSeconds = timeInSeconds
        )
    }
}
