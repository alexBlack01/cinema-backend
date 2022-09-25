package ru.cinema.domain.tag.model

import java.util.*

data class Tag(
    val id: UUID,
    val tagName: String,
    val categoryName: String
)
