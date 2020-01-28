package io.indrian.moviecatalogue.ui.movieinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import io.indrian.moviecatalogue.data.repositories.Repository
import io.indrian.moviecatalogue.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieInfoVM(
    val handle: SavedStateHandle,
    private val repository: Repository
): BaseViewModel() {

    private val mutMovieDetailState = MutableLiveData<MovieDetailState>()
    val movieDetailState: LiveData<MovieDetailState>
        get() = mutMovieDetailState

    fun getMovieDetail(id: Int) {

        val disposable = repository.getMovieDetail(id, getLanguage())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mutMovieDetailState.value = MovieDetailState.Loading }
            .subscribe(
                { movieDetail ->

                    d { movieDetail.toString() }
                    mutMovieDetailState.value = MovieDetailState.Loaded(movieDetail)
                },
                { error ->

                    error.printStackTrace()
                    e { error.message.toString() }
                    mutMovieDetailState.value = MovieDetailState.Error(error.message!!)
                }
            )

        compositeDisposable.add(disposable)
    }
}