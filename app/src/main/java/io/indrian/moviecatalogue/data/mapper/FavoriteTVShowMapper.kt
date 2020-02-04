package io.indrian.moviecatalogue.data.mapper

import io.indrian.moviecatalogue.data.db.table.FavoriteTVShow
import io.indrian.moviecatalogue.data.model.TVShow
import java.util.*

class FavoriteTVShowMapper : BaseMapper<FavoriteTVShow, TVShow>() {

    override fun toModel(entity: FavoriteTVShow): TVShow {

        return TVShow(
            id = entity.id,
            name = entity.name,
            poster = entity.poster,
            backdrop = entity.backdrop,
            releaseDate = entity.releaseDate,
            overview = entity.overview,
            voteAverage = entity.voteAverage,
            voteCount = entity.voteCount
        )
    }

    override fun toEntity(model: TVShow): FavoriteTVShow {

        return FavoriteTVShow(
            id = model.id,
            name = model.name,
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