@file:Suppress("MatchingDeclarationName", "Filename")

package ru.cinema.api.preference.route

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Serializable
@Resource("preferences")
class Preferences
