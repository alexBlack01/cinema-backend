package ru.cinema.data.db.tag

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import ru.cinema.data.db.common.extensions.DatabaseDataSource
import ru.cinema.data.db.tag.model.TagEntity
import ru.cinema.data.db.tag.model.TagTable
import ru.cinema.domain.tag.TagDbDataSource
import ru.cinema.domain.tag.model.EditTagForm
import ru.cinema.domain.tag.model.Tag
import ru.cinema.domain.tag.model.TagForm
import java.util.*

class TagDbDataSourceImpl(
    override val db: Database
) : TagDbDataSource, DatabaseDataSource {

    override suspend fun getAllTags(): List<Tag> = dbQuery {
        getAll()
    }

    override suspend fun getTagById(tagId: UUID): Tag? = dbQuery {
        TagEntity.findById(tagId)?.toDomain()
    }

    override suspend fun isTagExists(tagInfo: TagForm): Boolean = dbQuery {
        TagEntity.find { TagTable.tagName eq tagInfo.tagName and (TagTable.categoryName eq tagInfo.categoryName) }.any()
    }

    override suspend fun insertNewTag(tagInfo: TagForm): Tag = dbQuery {
        val tag = TagEntity.new {
            this.tagName = tagInfo.tagName
            this.categoryName = tagInfo.categoryName
        }

        tag.toDomain()
    }

    override suspend fun editTag(editTagForm: EditTagForm): Tag? = dbQuery {
        val tag = TagEntity.findById(editTagForm.id)?.apply {
            editTagForm.tagName?.let { tagName ->
                this.tagName = tagName
            }
            editTagForm.categoryName?.let { categoryName ->
                this.categoryName = categoryName
            }
        }

        tag?.toDomain()
    }

    override suspend fun deleteTagById(tagId: UUID) = dbQueryWithoutResult {
        TagTable.deleteWhere { TagTable.id eq tagId }
    }

    private fun getAll(): List<Tag> = TagEntity
        .all()
        .orderBy(TagTable.tagName to SortOrder.ASC)
        .map { it.toDomain() }
}
