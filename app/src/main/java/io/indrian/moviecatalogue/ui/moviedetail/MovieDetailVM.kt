package io.indrian.moviecatalogue.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import io.indrian.moviecatalogue.data.repositories.Repository
import io.indrian.moviecatalogue.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailVM(private val repository: Repository) : BaseViewModel() {

    private val mutMovieGenreState = MutableLiveData<MovieGenreState>()
    val movieGenreState: LiveData<MovieGenreState>
        get() = mutMovieGenreState

    fun getMovieGenres(id: Int) {

        val disposable = repository.getMovieDetail(id, getLanguage())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mutMovieGenreState.value = MovieGenreState.Loading }
            .subscribe(

                { movieDetail ->

                    d { "Genres: ${movieDetail.genres}" }
                    mutMovieGenreState.value = MovieGenreState.Loaded(movieDetail.genres)
                },
                { error ->

                    error.printStackTrace()
                    e { "Error: $error" }
                    mutMovieGenreState.value = MovieGenreState.Error(error.message!!)
                }
            )

        compositeDisposable.add(disposable)
    }
}