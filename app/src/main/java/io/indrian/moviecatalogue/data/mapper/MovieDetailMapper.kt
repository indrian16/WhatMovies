package io.indrian.moviecatalogue.data.mapper

import io.indrian.moviecatalogue.data.entity.MovieDetailEntity
import io.indrian.moviecatalogue.data.model.Genre
import io.indrian.moviecatalogue.data.model.MovieDetail

class MovieDetailMapper : BaseMapper<MovieDetailEntity, MovieDetail>() {

    override fun toModel(entity: MovieDetailEntity): MovieDetail {

        return MovieDetail(
            id = entity.id!!,
            backdropPath = backdropUrl+entity.backdropPath,
            overview = safeOverview(entity.overview!!),
            genres = entity.genres!!.map {

                Genre(
                    id = it?.id!!,
                    name = it.name!!
                )
            },
            posterPath = posterUrl+entity.posterPath,
            releaseDate = parseDate(entity.releaseDate!!),
            title = entity.title!!,
            voteCount = entity.voteCount!!,
            voteAverage = entity.voteAverage!!
        )
    }
}