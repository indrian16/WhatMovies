package io.indrian.moviecatalogue.data.mapper

import io.indrian.moviecatalogue.data.entity.DetailTVShowEntity
import io.indrian.moviecatalogue.data.model.TVShowDetail
import io.indrian.moviecatalogue.data.model.Genre

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