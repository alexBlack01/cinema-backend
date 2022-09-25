@file:Suppress("MatchingDeclarationName", "Filename")

package ru.cinema.app.docs

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Serializable
@Resource("/docs")
class GetDocs
