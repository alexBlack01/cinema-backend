package ru.cinema.api.auth.model

import io.konform.validation.Validation
import io.konform.validation.jsonschema.minLength
import kotlinx.serialization.Serializable
import ru.cinema.api.common.request.ValidatedBody
import ru.cinema.api.common.validation.isEmail
import ru.cinema.api.common.validation.isNotBlank

@Serializable
data class LoginViaEmailBody(
    val email: String,
    val password: String
) : ValidatedBody<LoginViaEmailBody>() {
    override val validationRule: Validation<LoginViaEmailBody> = Validation {
        LoginViaEmailBody::email required {
            minLength(length = 1)
            isNotBlank()
            isEmail()
        }
        LoginViaEmailBody::password required {
            minLength(length = 8)
            isNotBlank()
        }
    }
}
