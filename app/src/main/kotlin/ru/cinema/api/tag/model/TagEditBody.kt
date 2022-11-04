package ru.cinema.api.tag.model

import io.konform.validation.Validation
import io.konform.validation.jsonschema.minLength
import kotlinx.serialization.Serializable
import ru.cinema.api.common.request.ValidatedBody
import ru.cinema.api.common.validation.isNotBlank
import ru.cinema.domain.tag.model.EditTagForm
import java.util.UUID

@Serializable
data class TagEditBody(
    val tagName: String? = null,
    val categoryName: String? = null
) : ValidatedBody<TagEditBody>() {
    override val validationRule: Validation<TagEditBody> = Validation {
        TagEditBody::tagName ifPresent {
            minLength(length = 1)
            isNotBlank()
        }
        TagEditBody::categoryName ifPresent {
            minLength(length = 1)
            isNotBlank()
        }
    }

    fun toDomain(tagId: UUID) = EditTagForm(
        id = tagId,
        tagName = tagName,
        categoryName = categoryName
    )
}
