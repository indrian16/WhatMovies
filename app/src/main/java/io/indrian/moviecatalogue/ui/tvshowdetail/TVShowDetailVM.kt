package io.indrian.moviecatalogue.ui.tvshowdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.github.ajalt.timberkt.Timber.e
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
}