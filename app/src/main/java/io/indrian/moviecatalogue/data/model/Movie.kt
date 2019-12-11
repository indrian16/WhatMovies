package io.indrian.moviecatalogue.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val poster: String,
    val backdrop: String,
    val year: String,
    val overview: String
): Parcelable