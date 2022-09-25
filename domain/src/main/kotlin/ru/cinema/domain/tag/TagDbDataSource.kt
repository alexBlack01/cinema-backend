package ru.cinema.domain.tag

import ru.cinema.domain.tag.model.Tag

interface TagDbDataSource {
    suspend fun getAllTags(): List<Tag>
}
