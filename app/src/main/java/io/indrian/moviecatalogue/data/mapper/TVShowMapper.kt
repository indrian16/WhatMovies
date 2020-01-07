package io.indrian.moviecatalogue.data.mapper

import io.indrian.moviecatalogue.data.entity.TVShowEntity
import io.indrian.moviecatalogue.data.model.TVShow
import java.text.SimpleDateFormat
import java.util.*

class TVShowMapper : BaseMapper<TVShowEntity, TVShow> {

    override fun toModel(entity: TVShowEntity): TVShow {

        return TVShow(
            id = entity.id!!,
            name = entity.name!!,
            poster = "https://image.tmdb.org/t/p/w342"+(entity.posterPath ?: ""),
            backdrop = "https://image.tmdb.org/t/p/w342"+(entity.backdropPath ?: ""),
            releaseDate = parseDate(entity.firstAirDate!!),
            overview = safeOverview(entity.overview!!)
        )
    }

    private fun safeOverview(overview: String):String {

        return if (overview.isEmpty()) {

            "Not Support this language"
        } else {

            overview
        }
    }

    private fun parseDate(dateStr: String): Calendar {

        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        df.parse(dateStr)

        return df.calendar
    }
}