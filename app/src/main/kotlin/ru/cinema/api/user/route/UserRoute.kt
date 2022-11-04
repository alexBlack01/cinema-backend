@file:Suppress("MatchingDeclarationName", "Filename")

package ru.cinema.api.user.route

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Serializable
@Resource("profile")
class Profile

@Serializable
@Resource("profile/avatar")
class PostAvatar
