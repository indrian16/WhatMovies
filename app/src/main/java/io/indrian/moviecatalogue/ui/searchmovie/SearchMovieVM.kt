package io.indrian.moviecatalogue.ui.searchmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.github.ajalt.timberkt.d
import io.indrian.moviecatalogue.data.repositories.Repository
import io.indrian.moviecatalogue.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchMovieVM(
    handle: SavedStateHandle,
    private val repository: Repository
) : BaseViewModel() {

    private val mutSearchMovieState = MutableLiveData<SearchMovieState>()
    val searchMovieState: LiveData<SearchMovieState>
        get() = mutSearchMovieState

    init {

        mutSearchMovieState.value = SearchMovieState.InitState
    }

    fun searchMovie(query: String) {

        val disposable = repository.getSearchMovie(query, getLanguage())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mutSearchMovieState.value = SearchMovieState.Loading }
            .subscribe(

                { movies ->

                    d { "movies: ${movies.size}" }
                    if (movies.isNotEmpty()) {

                        mutSearchMovieState.value = SearchMovieState.Loaded(movies)
                    } else {

                        mutSearchMovieState.value = SearchMovieState.NotFound
                    }
                },
                { error ->

                    val errorMessage = error.message.toString()
                    d { "error: $errorMessage" }
                    mutSearchMovieState.value = SearchMovieState.Error(errorMessage)
                }
            )

        compositeDisposable.add(disposable)
    }
}