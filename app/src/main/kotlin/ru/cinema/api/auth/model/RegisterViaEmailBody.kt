package ru.cinema.api.auth.model

import io.konform.validation.Validation
import io.konform.validation.jsonschema.minLength
import kotlinx.serialization.Serializable
import ru.cinema.api.common.request.ValidatedBody
import ru.cinema.api.common.validation.isEmail
import ru.cinema.api.common.validation.isNotBlank
import ru.cinema.domain.auth.model.RegisterViaEmailParams

@Serializable
data class RegisterViaEmailBody(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String
) : ValidatedBody<RegisterViaEmailBody>() {
    override val validationRule: Validation<RegisterViaEmailBody> = Validation {
        RegisterViaEmailBody::email required {
            minLength(length = 1)
            isNotBlank()
            isEmail()
        }
        RegisterViaEmailBody::password required {
            minLength(length = 8)
            isNotBlank()
        }
        RegisterViaEmailBody::firstName required {
            minLength(length = 1)
            isNotBlank()
        }
        RegisterViaEmailBody::lastName required {
            minLength(length = 1)
            isNotBlank()
        }
    }

    fun toDomain() = RegisterViaEmailParams(
        email = email,
        password = password,
        firstName = firstName,
        lastName = lastName
    )
}
