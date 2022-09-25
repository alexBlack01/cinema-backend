@file:Suppress("MatchingDeclarationName", "Filename")

package ru.cinema.api.tag.route

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Serializable
@Resource("tags")
class Tags
