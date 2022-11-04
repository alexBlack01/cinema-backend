package ru.cinema.data.db.dislike.model

import org.jetbrains.exposed.sql.Table
import ru.cinema.data.db.movie.model.MovieTable
import ru.cinema.data.db.user.model.UserTable

object DislikeTable : Table(name = "user_dislikes") {
    val userId = reference(name = "user_id", foreign = UserTable)
    val movieId = reference(name = "movie_id", foreign = MovieTable)
    override val primaryKey = PrimaryKey(userId, movieId, name = "PK_UserMovie_user_id_movie_id")
}
