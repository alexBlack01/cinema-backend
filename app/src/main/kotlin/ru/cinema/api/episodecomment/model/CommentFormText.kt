package ru.cinema.api.episodecomment.model

import io.konform.validation.Validation
import io.konform.validation.jsonschema.minLength
import kotlinx.serialization.Serializable
import ru.cinema.api.common.request.ValidatedBody
import ru.cinema.api.common.validation.isNotBlank

@Serializable
data class CommentFormText(
    val text: String
) : ValidatedBody<CommentFormBody>() {
    override val validationRule: Validation<CommentFormBody> = Validation {
        CommentFormBody::text required {
            minLength(1)
            isNotBlank()
        }
    }
}
