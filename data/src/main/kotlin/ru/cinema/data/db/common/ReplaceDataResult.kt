package ru.cinema.data.db.common

data class ReplaceDataResult<T : Any>(
    val dataToInsert: List<T>,
    val dataToDelete: List<T>
)
