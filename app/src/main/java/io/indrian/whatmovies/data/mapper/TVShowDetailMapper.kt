package io.indrian.whatmovies.data.mapper

import io.indrian.whatmovies.data.entity.DetailTVShowEntity
import io.indrian.whatmovies.data.model.TVShowDetail
import io.indrian.whatmovies.data.model.Genre

class TVShowDetailMapper: BaseMapper<DetailTVShowEntity, TVShowDetail>() {

    override fun toModel(entity: DetailTVShowEntity): TVShowDetail {

        return TVShowDetail(
            backdrop = getBackdropPath(entity.backdropPath),
            posterPath = getPosterPath(entity.posterPath),
            firstAirDate = parseDate(entity.firstAirDate),
            genres = entity.genreEntities.map {

                Genre(
                    id = it.id,
                    name = it.name
                )
            },
            id = entity.id,
            name = entity.name,
            overview = safeOverview(entity.overview),
            popularity = entity.popularity,
            voteAverage = entity.voteAverage,
            voteCount = entity.voteCount
        )
    }

    override fun toEntity(model: TVShowDetail): DetailTVShowEntity {

        return DetailTVShowEntity()
    }
}