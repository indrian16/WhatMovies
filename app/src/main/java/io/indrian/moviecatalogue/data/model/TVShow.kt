package io.indrian.moviecatalogue.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.indrian.moviecatalogue.utils.Constant
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = Constant.TABLE_TVSHOW)
@Parcelize
data class TVShow(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "poster") val poster: String,
    @ColumnInfo(name = "backdrop") val backdrop: String,
    @ColumnInfo(name = "release_date") val releaseDate: Date,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "vote_count") val voteCount: Int
): Parcelable