package io.indrian.whatmovies.data.db.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.indrian.whatmovies.utils.Constant
import java.util.*

@Entity(tableName = Constant.TABLE_TV_SHOW_FAVORITE)
data class FavoriteTVShow(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "poster")val poster: String,
    @ColumnInfo(name = "backdrop")val backdrop: String,
    @ColumnInfo(name = "release_date") val releaseDate: Date,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "vote_count") val voteCount: Int,
    @ColumnInfo(name = "create_at") val createAt: Date
)