package io.indrian.moviecatalogue.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val poster: String,
    val backdrop: String,
    val releaseDate: Calendar,
    val overview: String,
    val voteAverage: Double,
    val voteCount: Int
): Parcelable