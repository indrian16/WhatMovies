package io.indrian.moviecatalogue.data.repositories

import android.content.Context
import com.github.ajalt.timberkt.d
import io.indrian.moviecatalogue.data.model.Movie
import io.indrian.moviecatalogue.data.model.MovieDetail
import io.indrian.moviecatalogue.data.model.TVShow
import io.indrian.moviecatalogue.data.model.TVShowDetail
import io.indrian.moviecatalogue.utils.isNetworkConnected
import io.reactivex.Observable

class Repository(
    private val context: Context,
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {

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

    fun getTVShowDetail(id: Int, language: String): Observable<TVShowDetail> = remoteRepository.getTVShowDetail(id, language)

    fun getMovieDetail(id: Int, language: String): Observable<MovieDetail> = remoteRepository.getMovieDetail(id, language)
}