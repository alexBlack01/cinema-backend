@file:Suppress("MatchingDeclarationName", "Filename")

package ru.cinema.api.chat.route

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@Resource("chats")
class Chats
