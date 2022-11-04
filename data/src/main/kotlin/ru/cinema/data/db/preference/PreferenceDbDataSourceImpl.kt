package ru.cinema.data.db.preference

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import ru.cinema.data.db.common.DataReplacerUtils
import ru.cinema.data.db.common.extensions.DatabaseDataSource
import ru.cinema.data.db.tag.model.TagTable
import ru.cinema.data.db.user.model.UserEntity
import ru.cinema.data.db.usertag.model.UserTagTable
import ru.cinema.domain.preference.PreferenceDbDataSource
import ru.cinema.domain.preference.model.PreferencesForm
import ru.cinema.domain.tag.model.Tag
import java.util.*

class PreferenceDbDataSourceImpl(
    override val db: Database
) : PreferenceDbDataSource, DatabaseDataSource {

    override suspend fun getPreferencesByUser(userId: UUID): List<Tag> = dbQuery {
        getAllByUser(userId)
    }

    override suspend fun updatePreferencesByUser(preferencesForm: PreferencesForm) =
        dbQueryWithoutResult {
            val existPreferences = getAllByUser(preferencesForm.userId)
            val replaceResult = DataReplacerUtils.compareData(
                newData = preferencesForm.preferences,
                existData = existPreferences,
                idExtractor = { it.id }
            )

            with(replaceResult) {
                deletePreferences(preferencesForm.userId, dataToDelete.map { it.id })
                dataToInsert.forEach { preference -> insertPreference(preferencesForm.userId, preference) }
            }
        }

    private fun getAllByUser(userId: UUID): List<Tag> = UserEntity
        .findById(userId)?.tags
        ?.orderBy(TagTable.tagName to SortOrder.ASC)
        ?.map { it.toDomain() } ?: emptyList()

    private fun deletePreferences(userId: UUID, preferenceIds: List<UUID>) {
        UserTagTable.deleteWhere {
            UserTagTable.tagId inList preferenceIds and (UserTagTable.userId eq userId)
        }
    }

    private fun insertPreference(userId: UUID, preference: Tag) {
        UserTagTable.insert {
            it[this.userId] = userId
            it[this.tagId] = preference.id
        }
    }
}
