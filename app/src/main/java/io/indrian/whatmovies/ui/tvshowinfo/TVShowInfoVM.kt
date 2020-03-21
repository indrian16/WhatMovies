package io.indrian.whatmovies.ui.tvshowinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import io.indrian.whatmovies.data.repositories.Repository
import io.indrian.whatmovies.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TVShowInfoVM(
    val savedStateHandle: SavedStateHandle,
    private val repository: Repository
): BaseViewModel() {

    private val mutTVShowDetailState = MutableLiveData<TVShowDetailState>()
    val tvShowDetailState: LiveData<TVShowDetailState>
        get() = mutTVShowDetailState

    fun getTVShowDetail(id: Int) {

        val disposable = repository.getTVShowDetail(id, getLanguage())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mutTVShowDetailState.value = TVShowDetailState.Loading }
            .subscribe(

                { tvShowDetail ->

                    d { tvShowDetail.toString() }
                    mutTVShowDetailState.value = TVShowDetailState.Loaded(tvShowDetail)
                },
                { error ->

                    error.printStackTrace()
                    e { error.message.toString() }
                    mutTVShowDetailState.value = TVShowDetailState.Error(error.message.toString())
                }
            )

        compositeDisposable.add(disposable)
    }
}