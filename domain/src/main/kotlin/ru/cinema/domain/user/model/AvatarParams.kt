package ru.cinema.domain.user.model

import ru.cinema.domain.movie.model.ContentForm
import java.util.*

data class AvatarParams(
    val userId: UUID,
    val avatar: ContentForm?
)
