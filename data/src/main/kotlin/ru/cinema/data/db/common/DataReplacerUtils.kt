package ru.cinema.data.db.common

import java.util.UUID

object DataReplacerUtils {
    fun <T : Any> compareData(newData: List<T>, existData: List<T>, idExtractor: (T) -> UUID): ReplaceDataResult<T> {
        val existIds = existData.map(idExtractor)
        val newIds = newData.map(idExtractor)
        val dataToInsert = newData.filter { idExtractor.invoke(it) !in existIds }
        val dataToDelete = existData.filter { idExtractor.invoke(it) !in newIds }

        return ReplaceDataResult(
            dataToInsert = dataToInsert,
            dataToDelete = dataToDelete
        )
    }
}
