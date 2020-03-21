package io.indrian.whatmovies.data.mapper

import io.indrian.whatmovies.data.entity.MovieEntity
import io.indrian.whatmovies.data.model.Movie

class MovieMapper: BaseMapper<MovieEntity, Movie>() {

    override fun toModel(entity: MovieEntity): Movie {

        return Movie(
            id = entity.id,
            title = entity.title,
            poster = getPosterPath(entity.posterPath),
            backdrop = getBackdropPath(entity.backdropPath),
            releaseDate = parseDate(entity.releaseDate),
            overview = safeOverview(entity.overview),
            voteCount = entity.voteCount,
            voteAverage = entity.voteAverage
        )
    }

    override fun toEntity(model: Movie): MovieEntity {

        return MovieEntity()
    }
}