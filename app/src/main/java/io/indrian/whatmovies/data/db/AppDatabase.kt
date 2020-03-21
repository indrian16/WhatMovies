package io.indrian.whatmovies.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.indrian.whatmovies.data.db.dao.FavoriteDao
import io.indrian.whatmovies.data.db.dao.MovieDao
import io.indrian.whatmovies.data.db.dao.TVShowDao
import io.indrian.whatmovies.data.db.table.FavoriteMovie
import io.indrian.whatmovies.data.db.table.FavoriteTVShow
import io.indrian.whatmovies.data.model.Movie
import io.indrian.whatmovies.data.model.TVShow
import io.indrian.whatmovies.utils.Constant

@Database(
    entities = [Movie::class, TVShow::class, FavoriteMovie::class, FavoriteTVShow::class],
    version = Constant.DB_VER
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun tvShowDao(): TVShowDao

    abstract fun favoriteDao(): FavoriteDao
}