package io.indrian.moviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.ajalt.timberkt.Timber.e
import com.github.ajalt.timberkt.Timber.d
import io.indrian.moviecatalogue.data.repositories.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieVM(private val repository: Repository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val mutMovieListState = MutableLiveData<MoviesListState>()
    val movieListState: LiveData<MoviesListState>
        get() = mutMovieListState

    fun getMovies() {

        val disposable = repository.getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mutMovieListState.value = MoviesListState.Loading }
            .subscribe(
                { movies ->

                    d { "MovieLoaded: ${movies.size} size" }
                    mutMovieListState.value = MoviesListState.Loaded(movies)
                },
                { error ->

                    e { error.message.toString() }
                    mutMovieListState.value = MoviesListState.Error(error.message.toString())
                }
            )

        compositeDisposable.addAll(disposable)
    }

    override fun onCleared() {

        compositeDisposable.clear()
        super.onCleared()
    }
}