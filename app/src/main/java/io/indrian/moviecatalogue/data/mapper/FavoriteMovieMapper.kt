package io.indrian.moviecatalogue.data.mapper

import io.indrian.moviecatalogue.data.db.table.FavoriteMovie
import io.indrian.moviecatalogue.data.model.Movie
import java.util.*

class FavoriteMovieMapper : BaseMapper<FavoriteMovie, Movie>() {

    override fun toModel(entity: FavoriteMovie): Movie {

        return Movie(
            id = entity.id,
            title = entity.title,
            poster = entity.poster,
            backdrop = entity.backdrop,
            releaseDate = entity.releaseDate,
            overview = entity.overview,
            voteAverage = entity.voteAverage,
            voteCount = entity.voteCount
        )
    }

    override fun toEntity(model: Movie): FavoriteMovie {

        return FavoriteMovie(
            id = model.id,
            title = model.title,
            poster = model.poster,
            backdrop = model.backdrop,
            releaseDate = model.releaseDate,
            overview = model.overview,
            voteAverage = model.voteAverage,
            voteCount = model.voteCount,
            createAt = Date(System.currentTimeMillis())
        )
    }
}