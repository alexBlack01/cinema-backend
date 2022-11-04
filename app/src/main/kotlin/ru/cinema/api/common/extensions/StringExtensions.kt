package ru.cinema.api.common.extensions

fun String.toResourceUrl(
    baseUrl: String,
    uploadFolder: String,
    folder: String
) = "${baseUrl}$uploadFolder/$folder/$this"
