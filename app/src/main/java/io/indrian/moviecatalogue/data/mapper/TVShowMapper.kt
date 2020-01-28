package io.indrian.moviecatalogue.data.mapper

import io.indrian.moviecatalogue.data.entity.TVShowEntity
import io.indrian.moviecatalogue.data.model.TVShow
import javax.inject.Inject

class TVShowMapper @Inject constructor() : BaseMapper<TVShowEntity, TVShow>() {

    override fun toModel(entity: TVShowEntity): TVShow {

        return TVShow(
            id = entity.id!!,
            name = entity.name!!,
            poster = "https://image.tmdb.org/t/p/w342"+(entity.posterPath ?: ""),
            backdrop = "https://image.tmdb.org/t/p/w342"+(entity.backdropPath ?: ""),
            releaseDate = parseDate(entity.firstAirDate!!),
            overview = safeOverview(entity.overview!!),
            voteAverage = entity.voteAverage!!,
            voteCount = entity.voteCount!!
        )
    }

}