@file:Suppress("MatchingDeclarationName", "Filename")

package ru.cinema.api.userhistory.route

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Serializable
@Resource("history")
class UserHistory
