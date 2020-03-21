package io.indrian.whatmovies.data.repositories

import io.indrian.whatmovies.data.db.dao.FavoriteDao
import io.indrian.whatmovies.data.db.dao.MovieDao
import io.indrian.whatmovies.data.db.dao.TVShowDao
import io.indrian.whatmovies.data.mapper.FavoriteMovieMapper
import io.indrian.whatmovies.data.mapper.FavoriteTVShowMapper
import io.indrian.whatmovies.data.model.Movie
import io.indrian.whatmovies.data.model.TVShow
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

class LocalRepository(
    private val movieDao: MovieDao,
    private val tvShowDao: TVShowDao,
    private val favoriteDao: FavoriteDao,
    private val favoriteMovieMapper: FavoriteMovieMapper,
    private val favoriteTVShowMapper: FavoriteTVShowMapper
) {

    /***
     *
     *  Movie DAO
     */
    fun getMovies() = movieDao.getAllMovies()
    fun addAllMovie(movies: List<Movie>) = movieDao.insertAllMovie(movies)

    /**
     *
     *  TV Show DAO
     * */
    fun getTVShowList() = tvShowDao.getAllTVShowList()
    fun addAllTVShow(tvShowList: List<TVShow>) = tvShowDao.insetAllTVShowList(tvShowList)

    /**
     *
     *  Favorite Dao
     *  Movie
     * */
    fun getFavoriteMovies(): Observable<List<Movie>> =

        favoriteDao.getAllFavoriteMovie()
            .toObservable()
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

    /**
     *
     *  Favorite Dao
     *  TVShow
     * */
    fun getFavoriteTVShow(): Observable<List<TVShow>> =

        favoriteDao.getAllFavoriteTVShow()
            .toObservable()
            .flatMapIterable { it }
            .map { favoriteTVShowMapper.toModel(it) }
            .toList()
            .toObservable()

    fun getFavoriteTVShowIsExist(id: Int): Observable<Boolean> {

        return favoriteDao.getTVShowById(id)
            .map {

                it.isNotEmpty()
            }
    }
    fun addFavoriteTVShow(tvShow: TVShow): Maybe<Long> = favoriteDao.insertFavoriteTVShow(favoriteTVShowMapper.toEntity(tvShow))
    fun deleteFavoriteTVShow(tvShow: TVShow): Single<Int> = favoriteDao.deleteFavoriteTVShow(favoriteTVShowMapper.toEntity(tvShow))
}