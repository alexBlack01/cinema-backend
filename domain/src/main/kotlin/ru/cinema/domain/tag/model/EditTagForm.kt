package ru.cinema.domain.tag.model

import java.util.*

data class EditTagForm(
    val id: UUID,
    val tagName: String?,
    val categoryName: String?
)
