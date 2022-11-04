package ru.cinema.api.tag.model

import io.konform.validation.Validation
import io.konform.validation.jsonschema.minLength
import kotlinx.serialization.Serializable
import ru.cinema.api.common.request.ValidatedBody
import ru.cinema.api.common.validation.isNotBlank
import ru.cinema.domain.tag.model.TagForm

@Serializable
data class TagBody(
    val tagName: String,
    val categoryName: String
) : ValidatedBody<TagBody>() {
    override val validationRule: Validation<TagBody> = Validation {
        TagBody::tagName required {
            minLength(length = 1)
            isNotBlank()
        }
        TagBody::categoryName required {
            minLength(length = 1)
            isNotBlank()
        }
    }

    fun toDomain() = TagForm(
        tagName = tagName,
        categoryName = categoryName
    )
}
