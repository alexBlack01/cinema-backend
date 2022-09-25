package ru.cinema.data.db.common.extensions

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

interface DatabaseDataSource {
    val db: Database

    suspend fun <T> dbQuery(block: () -> T): T =
        transaction(db = db) { block.invoke() }

    suspend fun dbQueryWithoutResult(block: () -> Unit) =
        transaction(db = db) { block.invoke() }
}
