package io.indrian.moviecatalogue.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
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
): Parcelable