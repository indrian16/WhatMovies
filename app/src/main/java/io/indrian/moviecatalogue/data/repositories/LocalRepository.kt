package io.indrian.moviecatalogue.data.repositories

import io.indrian.moviecatalogue.data.db.dao.FavoriteDao
import io.indrian.moviecatalogue.data.db.dao.MovieDao
import io.indrian.moviecatalogue.data.db.dao.TVShowDao
import io.indrian.moviecatalogue.data.mapper.FavoriteMovieMapper
import io.indrian.moviecatalogue.data.model.Movie
import io.indrian.moviecatalogue.data.model.TVShow
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

class LocalRepository(
    private val movieDao: MovieDao,
    private val tvShowDao: TVShowDao,
    private val favoriteDao: FavoriteDao,
    private val favoriteMovieMapper: FavoriteMovieMapper
) {

    /***
     *
     * Movie DAO
     */
    fun getMovies() = movieDao.getAllMovies()
    fun addAllMovie(movies: List<Movie>) = movieDao.insertAllMovie(movies)

    /**
     *
     * TV Show DAO
     * */
    fun getTVShowList() = tvShowDao.getAllTVShowList()
    fun addAllTVShow(tvShowList: List<TVShow>) = tvShowDao.insetAllTVShowList(tvShowList)

    /**
     *
     * Favorite Dao
     * */
    fun getFavoritesMovies(): Observable<List<Movie>> =

        favoriteDao.getAllFavoriteMovie()
            .flatMapIterable { it }
            .map { favoriteMovieMapper.toModel(it) }
            .toList()
            .toObservable()

    fun getFavoriteMovieIsExist(id: Int): Observable<Boolean> {

        return favoriteDao.getMovieById(id)
            .map {

                it.isNotEmpty()
            }
    }
    fun addFavoriteMovie(movie: Movie): Maybe<Long> = favoriteDao.insertFavoriteMovie(favoriteMovieMapper.toEntity(movie))
    fun deleteFavoriteMovie(movie: Movie): Single<Int> = favoriteDao.deleteFavoriteMovie(favoriteMovieMapper.toEntity(movie))
}