package ru.cinema.data.db.tagmovie.model

import org.jetbrains.exposed.sql.Table
import ru.cinema.data.db.movie.model.MovieTable
import ru.cinema.data.db.tag.model.TagTable

object TagMovieTable : Table(name = "tags_movies") {
    val tagId = reference(name = "tag_id", foreign = TagTable)
    val movieId = reference(name = "movie_id", foreign = MovieTable)
    override val primaryKey = PrimaryKey(tagId, movieId, name = "PK_TagMovie_tag_id_movie_id")
}
