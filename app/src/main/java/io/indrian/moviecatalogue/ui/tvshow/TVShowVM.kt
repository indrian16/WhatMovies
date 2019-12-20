package io.indrian.moviecatalogue.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.ajalt.timberkt.Timber
import io.indrian.moviecatalogue.data.repositories.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TVShowVM(private val repository: Repository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val mutTVShowListState = MutableLiveData<TVShowListState>()
    val tvShowListState: LiveData<TVShowListState>
        get() = mutTVShowListState

    fun getTVShows() {

        val disposable = repository.getTVShows()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mutTVShowListState.value = TVShowListState.Loading }
            .subscribe(
                { movies ->

                    Timber.d { "MovieLoaded: ${movies.size} size" }
                    mutTVShowListState.value = TVShowListState.Loaded(movies)
                },
                { error ->

                    Timber.e { error.message.toString() }
                    mutTVShowListState.value = TVShowListState.Error(error.message.toString())
                }
            )

        compositeDisposable.addAll(disposable)
    }

    override fun onCleared() {

        compositeDisposable.clear()
        super.onCleared()
    }
}