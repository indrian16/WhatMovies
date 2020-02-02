package io.indrian.moviecatalogue.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.indrian.moviecatalogue.data.db.dao.MovieDao
import io.indrian.moviecatalogue.data.db.dao.TVShowDao
import io.indrian.moviecatalogue.data.model.Movie
import io.indrian.moviecatalogue.data.model.TVShow
import io.indrian.moviecatalogue.utils.Constant

@Database(
    entities = [Movie::class, TVShow::class],
    version = Constant.DB_VER
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun tvShowDao(): TVShowDao
}