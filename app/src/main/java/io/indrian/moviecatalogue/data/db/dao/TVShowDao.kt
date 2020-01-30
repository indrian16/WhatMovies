package io.indrian.moviecatalogue.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.indrian.moviecatalogue.data.model.TVShow
import io.reactivex.Single

@Dao
interface TVShowDao {

    @Query("SELECT * FROM tv_show")
    fun getAllTVShowList(): Single<List<TVShow>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insetAllTVShowList(tvShowList: List<TVShow>)
}