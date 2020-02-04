package io.indrian.moviecatalogue.ui.tvshowdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.github.ajalt.timberkt.Timber.d
import com.github.ajalt.timberkt.Timber.e
import io.indrian.moviecatalogue.data.model.TVShow
import io.indrian.moviecatalogue.data.repositories.Repository
import io.indrian.moviecatalogue.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TVShowDetailVM(
    val handle: SavedStateHandle,
    private val repository: Repository
) : BaseViewModel() {

    private val mutGenreTVShowState = MutableLiveData<GenreTVShowState>()
    val genreTVShowState: LiveData<GenreTVShowState>
        get() = mutGenreTVShowState

    private val mutFavoriteTVShowState = MutableLiveData<FavoriteTVShowState>()
    val favoriteTVShowState: LiveData<FavoriteTVShowState>
        get() = mutFavoriteTVShowState

    fun getDetailTVShow(id: Int) {

        val disposable = repository.getTVShowDetail(
            id,
            getLanguage()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mutGenreTVShowState.value = GenreTVShowState.Loading }
            .subscribe(
                { tvShowDetail ->

                    mutGenreTVShowState.value = GenreTVShowState.Loaded(tvShowDetail.genres)
                },
                { error ->

                    e { "Error: $error" }
                    mutGenreTVShowState.value = GenreTVShowState.Error(error.message.toString())
                }
            )

        compositeDisposable.add(disposable)
    }

    fun getTVShowIsExist(id: Int) {

        val disposable = repository.getFavoriteTVShowIsExit(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { exist ->

                    if (exist) {

                        mutFavoriteTVShowState.value = FavoriteTVShowState.Exist(false)
                    } else {

                        mutFavoriteTVShowState.value = FavoriteTVShowState.NotExist(false)
                    }
                },
                { error ->

                    error.printStackTrace()
                    val errorMessage = error.message.toString()
                    d { errorMessage }
                    mutFavoriteTVShowState.value = FavoriteTVShowState.Error(errorMessage)
                }
            )

        compositeDisposable.add(disposable)
    }

    fun addFavorite(tvShow: TVShow) {

        val disposable = repository.addFavoriteTVShow(tvShow)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    d { "Add id: $it" }
                    mutFavoriteTVShowState.value = FavoriteTVShowState.Exist(true)
                },
                { error ->

                    error.printStackTrace()
                    val errorMessage = error.message.toString()
                    d { errorMessage }
                    mutFavoriteTVShowState.value = FavoriteTVShowState.Error(errorMessage)
                }
            )

        compositeDisposable.add(disposable)
    }

    fun delFavorite(tvShow: TVShow) {

        val disposable = repository.deleteFavoriteTVShow(tvShow)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    d { "Delete Status: $it" }
                    mutFavoriteTVShowState.value = FavoriteTVShowState.NotExist(true)
                },
                { error ->

                    error.printStackTrace()
                    val errorMessage = error.message.toString()
                    d { errorMessage }
                    mutFavoriteTVShowState.value = FavoriteTVShowState.Error(errorMessage)
                }
            )

        compositeDisposable.add(disposable)
    }
}