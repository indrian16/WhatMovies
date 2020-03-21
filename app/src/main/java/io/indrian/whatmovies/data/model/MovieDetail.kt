package io.indrian.whatmovies.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MovieDetail(
    val backdropPath: String,
    val genres: List<Genre>,
    val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: Date,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int
): Parcelable