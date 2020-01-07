package io.indrian.moviecatalogue.data.mapper

import io.indrian.moviecatalogue.data.entity.MovieEntity
import io.indrian.moviecatalogue.data.model.Movie
import java.text.SimpleDateFormat
import java.util.*

class MovieMapper : BaseMapper<MovieEntity, Movie> {

    override fun toModel(entity: MovieEntity): Movie {

        return Movie(
            id = entity.id!!,
            title = entity.title!!,
            poster = "https://image.tmdb.org/t/p/w342"+(entity.posterPath ?: ""),
            backdrop = "https://image.tmdb.org/t/p/w780"+(entity.backdropPath ?: ""),
            releaseDate = parseDate(entity.releaseDate!!),
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