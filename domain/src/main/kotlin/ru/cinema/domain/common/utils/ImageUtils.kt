package ru.cinema.domain.common.utils

import java.util.*

object ImageUtils {

    fun generateFileName(fileType: String): String {
        val avatarName = UUID.randomUUID()

        return "$avatarName.$fileType"
    }
}
