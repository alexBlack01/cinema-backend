package ru.cinema.api.auth.model

import io.konform.validation.Validation
import io.konform.validation.jsonschema.minLength
import kotlinx.serialization.Serializable
import ru.cinema.api.common.request.ValidatedBody
import ru.cinema.api.common.validation.isNotBlank

@Serializable
data class RefreshTokenBody(
    val refreshToken: String
) : ValidatedBody<RefreshTokenBody>() {
    override val validationRule: Validation<RefreshTokenBody> = Validation {
        RefreshTokenBody::refreshToken required {
            minLength(1)
            isNotBlank()
        }
    }
}
