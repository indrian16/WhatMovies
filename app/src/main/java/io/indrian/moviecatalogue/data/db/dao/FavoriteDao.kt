package io.indrian.moviecatalogue.data.db.dao

import androidx.room.*
import io.indrian.moviecatalogue.data.db.table.FavoriteMovie
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite_movie ORDER BY create_at DESC")
    fun getAllFavoriteMovie(): Observable<List<FavoriteMovie>>

    @Query("SELECT * FROM favorite_movie WHERE id = :id")
    fun getMovieById(id: Int): Observable<List<FavoriteMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(movie: FavoriteMovie): Maybe<Long>

    @Delete
    fun deleteFavoriteMovie(movie: FavoriteMovie): Single<Int>
}