package ru.cinema.domain.common.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

object DateTimeUtils {

    fun getDateFromSeconds(value: Long): Date = Date.from(Instant.ofEpochSecond(value))

    fun getNowMillis(): Long = getNowInstant().toEpochMilli()

    fun getNowSeconds(): Long = getNowInstant().epochSecond

    fun getNowInstant(): Instant = Instant.now()

    fun getTodayStartOfDay(): LocalDateTime = LocalDate.now().atStartOfDay()

    fun addSecondsToStartOfDayInMillis(seconds: Long): Long =
        addSecondsToStartOfDay(seconds).toInstant(ZoneOffset.UTC).toEpochMilli()

    fun addSecondsToStartOfDay(seconds: Long): LocalDateTime =
        LocalDate.now().atStartOfDay().plusSeconds(seconds)
}
