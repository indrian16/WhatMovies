package io.indrian.moviecatalogue.data.model

import java.util.*

data class TVShowDetail(
    val backdrop: String,
    val firstAirDate: Calendar,
    val genres: List<Genre>,
    val id: Int,
    val name: String,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val voteAverage: Double?,
    val voteCount: Int
)