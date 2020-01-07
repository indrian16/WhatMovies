package io.indrian.moviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.Timber.e
import com.github.ajalt.timberkt.Timber.d
import io.indrian.moviecatalogue.data.repositories.Repository
import io.indrian.moviecatalogue.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieVM(private val repository: Repository) : BaseViewModel() {

    private val mutMovieListState = MutableLiveData<MoviesListState>()
    val movieListState: LiveData<MoviesListState>
        get() = mutMovieListState

    fun getMovies() {

        val disposable = repository.getMovies(getLanguage())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mutMovieListState.value = MoviesListState.Loading }
            .subscribe(
                { movies ->

                    d { "MovieLoaded: ${movies.size} size" }
                    mutMovieListState.value = MoviesListState.Loaded(movies)
                },
                { error ->

                    e { "Error: "+error.message }
                    mutMovieListState.value = MoviesListState.Error(error.message.toString())
                }
            )

        compositeDisposable.add(disposable)
    }
}