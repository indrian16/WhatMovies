package io.indrian.moviecatalogue.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.indrian.moviecatalogue.data.db.dao.FavoriteDao
import io.indrian.moviecatalogue.data.db.dao.MovieDao
import io.indrian.moviecatalogue.data.db.dao.TVShowDao
import io.indrian.moviecatalogue.data.db.table.FavoriteMovie
import io.indrian.moviecatalogue.data.model.Movie
import io.indrian.moviecatalogue.data.model.TVShow
import io.indrian.moviecatalogue.utils.Constant

@Database(
    entities = [Movie::class, TVShow::class, FavoriteMovie::class],
    version = Constant.DB_VER
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun tvShowDao(): TVShowDao

    abstract fun favoriteDao(): FavoriteDao
}