package io.indrian.moviecatalogue.data.model

import java.util.*

data class MovieDetail(
    val backdropPath: String,
    val genres: List<Genre>,
    val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: Calendar,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int
)