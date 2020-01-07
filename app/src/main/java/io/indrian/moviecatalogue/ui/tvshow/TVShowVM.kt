package io.indrian.moviecatalogue.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.Timber
import io.indrian.moviecatalogue.data.repositories.Repository
import io.indrian.moviecatalogue.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TVShowVM(private val repository: Repository) : BaseViewModel() {

    private val mutTVShowListState = MutableLiveData<TVShowListState>()
    val tvShowListState: LiveData<TVShowListState>
        get() = mutTVShowListState

    fun getTVShows() {

        val disposable = repository.getTVShow(getLanguage())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mutTVShowListState.value = TVShowListState.Loading }
            .subscribe(
                { tvShowList ->

                    Timber.d { "MovieLoaded: ${tvShowList.size} size" }
                    mutTVShowListState.value = TVShowListState.Loaded(tvShowList)
                },
                { error ->

                    error.printStackTrace()
                    Timber.e { "Error: "+error.message }
                    mutTVShowListState.value = TVShowListState.Error(error.message.toString())
                }
            )

        compositeDisposable.add(disposable)
    }
}