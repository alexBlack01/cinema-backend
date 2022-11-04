package ru.cinema.api.common.extensions

inline fun <reified T : Enum<T>> enumContains(name: String): Boolean {
    return enumValues<T>().any { it.name.lowercase() == name }
}
