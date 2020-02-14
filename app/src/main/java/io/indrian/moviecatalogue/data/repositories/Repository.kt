package io.indrian.moviecatalogue.data.repositories

import android.content.Context
import com.github.ajalt.timberkt.d
import io.indrian.moviecatalogue.data.model.Movie
import io.indrian.moviecatalogue.data.model.MovieDetail
import io.indrian.moviecatalogue.data.model.TVShow
import io.indrian.moviecatalogue.data.model.TVShowDetail
import io.indrian.moviecatalogue.utils.isNetworkConnected
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

class Repository(
    private val context: Context,
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {

    /**
     *
     * Provide to Movie ViewModel
     * */
    fun getMovies(language: String): Observable<List<Movie>> {

        return if (context.isNetworkConnected()) {

            remoteRepository.getMovies(language).doOnNext {

                d { "saveMovie(${it.size})" }
                localRepository.addAllMovie(it)
            }
        } else {

            d { "loadedMoviesFromSQLite" }
            localRepository.getMovies().toObservable()
        }
    }


    /**
     *
     * Provide to TV Show ViewModel
     * */
    fun getTVShow(language: String): Observable<List<TVShow>> {

        return if (context.isNetworkConnected()) {

            remoteRepository.getTVShow(language).doOnNext {

                d { "saveTVShow(${it.size})" }
                localRepository.addAllTVShow(it)
            }
        } else {

            d { "loadedTVShowFromSQLite" }
            localRepository.getTVShowList().toObservable()
        }
    }

    /**
     *
     * Provide to Detail TV Show ViewModel
     * */
    fun getTVShowDetail(id: Int, language: String): Observable<TVShowDetail> = remoteRepository.getTVShowDetail(id, language)
    fun getFavoriteTVShowIsExit(id: Int): Observable<Boolean> = localRepository.getFavoriteTVShowIsExist(id)
    fun addFavoriteTVShow(tvShow: TVShow): Maybe<Long> = localRepository.addFavoriteTVShow(tvShow)
    fun deleteFavoriteTVShow(tvShow: TVShow): Single<Int> = localRepository.deleteFavoriteTVShow(tvShow)

    /**
     *
     * Provide to Detail Movie ViewModel
     * */
    fun getMovieDetail(id: Int, language: String): Observable<MovieDetail> = remoteRepository.getMovieDetail(id, language)
    fun getFavoriteMovieIsExit(id: Int): Observable<Boolean> = localRepository.getFavoriteMovieIsExist(id)
    fun addFavoriteMovie(movie: Movie): Maybe<Long> = localRepository.addFavoriteMovie(movie)
    fun deleteFavoriteMovie(movie: Movie): Single<Int> = localRepository.deleteFavoriteMovie(movie)


    /**
     *
     * Provide to Favorite Movie ViewModel
     * */
    fun getFavoriteMovies(): Observable<List<Movie>> = localRepository.getFavoriteMovies()

    /**
     *
     * Provide to Favorite TV Show ViewModel
     * */
    fun getFavoriteTVShow(): Observable<List<TVShow>> = localRepository.getFavoriteTVShow()

    /**
     *
     *  Provide Search Movie ViewModel
     * */
    fun getSearchMovie(
        query: String,
        language: String
    ): Observable<List<Movie>> = remoteRepository.getSearchMovie(query, language)

    /**
     *
     *  Provide Search Movie ViewModel
     * */
    fun getSearchTVShow(
        query: String,
        language: String
    ): Observable<List<TVShow>> = remoteRepository.getSearchTVShow(query, language)

    /**
     *
     *  Provider New Movie Receiver
     * */
    fun getLatestMovieToday(dateGte: String, dateLte: String) = remoteRepository.getLatestMovieToday(dateGte, dateLte)
}