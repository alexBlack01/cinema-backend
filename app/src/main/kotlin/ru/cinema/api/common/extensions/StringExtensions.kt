package ru.cinema.api.common.extensions

fun String.toResourceUrl(baseUrl: String) = "${baseUrl}static/$this"
