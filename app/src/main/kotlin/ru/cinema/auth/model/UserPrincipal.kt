package ru.cinema.auth.model

import io.ktor.server.auth.Principal
import java.util.*

data class UserPrincipal(
    val userId: UUID
) : Principal
