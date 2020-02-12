package io.indrian.moviecatalogue.data.db.dao

import android.database.Cursor
import androidx.room.*
import io.indrian.moviecatalogue.data.db.table.FavoriteMovie
import io.indrian.moviecatalogue.data.db.table.FavoriteTVShow
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface FavoriteDao {

    /**
     *
     * Movie
     * */
    @Query("SELECT * FROM favorite_movie ORDER BY create_at DESC")
    fun getAllFavoriteMovie(): Single<List<FavoriteMovie>>
    @Query("SELECT * FROM favorite_movie WHERE id = :id")
    fun getMovieById(id: Int): Observable<List<FavoriteMovie>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(movie: FavoriteMovie): Maybe<Long>
    @Delete
    fun deleteFavoriteMovie(movie: FavoriteMovie): Single<Int>

    @Query("SELECT * FROM favorite_movie ORDER BY create_at DESC")
    fun getAllFavoriteMovieCursor(): Cursor

    /**
     *
     * TVShow
     * */
    @Query("SELECT * FROM favorite_tv_show ORDER BY create_at DESC")
    fun getAllFavoriteTVShow(): Single<List<FavoriteTVShow>>
    @Query("SELECT * FROM favorite_tv_show WHERE id = :id")
    fun getTVShowById(id: Int): Observable<List<FavoriteTVShow>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteTVShow(movie: FavoriteTVShow): Maybe<Long>
    @Delete
    fun deleteFavoriteTVShow(movie: FavoriteTVShow): Single<Int>
}