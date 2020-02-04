package io.indrian.moviecatalogue.ui.favoritetvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.github.ajalt.timberkt.Timber.d
import io.indrian.moviecatalogue.data.repositories.Repository
import io.indrian.moviecatalogue.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FavoriteTVShowVM(
    val handle: SavedStateHandle,
    private val repository: Repository
): BaseViewModel() {

    private val mutFavoriteTVShowListState = MutableLiveData<FavoriteTVShowListState>()
    val favoriteTVShowListState: LiveData<FavoriteTVShowListState>
        get() = mutFavoriteTVShowListState

    fun getFavoriteTVShow() {

        val disposable = repository.getFavoriteTVShow()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mutFavoriteTVShowListState.value = FavoriteTVShowListState.Loading }
            .subscribe(
                { tvShow ->

                    if (tvShow.isNotEmpty()) {

                        d { "TVShow: ${tvShow.size}" }
                        mutFavoriteTVShowListState.value = FavoriteTVShowListState.Loaded(tvShow)
                    } else {

                        mutFavoriteTVShowListState.value = FavoriteTVShowListState.Empty
                    }
                },
                { error ->

                    error.printStackTrace()
                    val errorMessage = error.message.toString()
                    d { "Error: $errorMessage" }
                    mutFavoriteTVShowListState.value = FavoriteTVShowListState.Error(errorMessage)
                }
            )

        compositeDisposable.add(disposable)
    }
}