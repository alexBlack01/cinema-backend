package ru.cinema.domain.collection.model

import java.util.UUID

data class Collection(
    val collectionId: UUID,
    val name: String
)
