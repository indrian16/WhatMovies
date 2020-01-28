package io.indrian.moviecatalogue.data.mapper

import io.indrian.moviecatalogue.data.entity.DetailTVShowEntity
import io.indrian.moviecatalogue.data.model.TVShowDetail
import io.indrian.moviecatalogue.data.model.Genre
import javax.inject.Inject

class TVShowDetailMapper @Inject constructor() : BaseMapper<DetailTVShowEntity, TVShowDetail>() {

    override fun toModel(entity: DetailTVShowEntity): TVShowDetail {

        return TVShowDetail(
            backdrop = "https://image.tmdb.org/t/p/w780"+entity.backdropPath,
            posterPath = "https://image.tmdb.org/t/p/w342"+entity.posterPath,
            firstAirDate = parseDate(entity.firstAirDate!!),
            genres = entity.genreEntities!!.map {

                Genre(
                    id = it?.id!!,
                    name = it.name!!
                )
            },
            id = entity.id!!,
            name = entity.name!!,
            overview = safeOverview(entity.overview!!),
            popularity = entity.popularity,
            voteAverage = entity.voteAverage,
            voteCount = entity.voteCount!!
        )
    }
}