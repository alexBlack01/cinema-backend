package ru.cinema.data.db.tag

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SortOrder
import ru.cinema.data.db.common.extensions.DatabaseDataSource
import ru.cinema.data.db.tag.model.TagEntity
import ru.cinema.data.db.tag.model.TagTable
import ru.cinema.domain.tag.TagDbDataSource
import ru.cinema.domain.tag.model.Tag

class TagDbDataSourceImpl(
    override val db: Database
) : TagDbDataSource, DatabaseDataSource {

    override suspend fun getAllTags(): List<Tag> = dbQuery {
        getAll()
    }

    private fun getAll(): List<Tag> = TagEntity
        .all()
        .orderBy(TagTable.tagName to SortOrder.ASC)
        .map { it.toDomain() }
}
