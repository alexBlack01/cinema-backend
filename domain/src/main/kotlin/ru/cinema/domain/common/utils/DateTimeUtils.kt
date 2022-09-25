package ru.cinema.domain.common.utils

import java.time.Instant
import java.util.*

object DateTimeUtils {

    fun getDateFromSeconds(value: Long): Date = Date.from(Instant.ofEpochSecond(value))

    fun getNowMillis(): Long = getNowInstant().toEpochMilli()

    fun getNowSeconds(): Long = getNowInstant().epochSecond

    fun getNowInstant(): Instant = Instant.now()
}
