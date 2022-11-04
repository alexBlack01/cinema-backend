package ru.cinema.api.collection.model

import io.konform.validation.Validation
import io.konform.validation.jsonschema.minLength
import kotlinx.serialization.Serializable
import ru.cinema.api.common.request.ValidatedBody
import ru.cinema.api.common.validation.isNotBlank

@Serializable
data class CollectionBody(
    val name: String
) : ValidatedBody<CollectionBody>() {
    override val validationRule: Validation<CollectionBody> = Validation {
        CollectionBody::name required {
            minLength(1)
            isNotBlank()
        }
    }
}
