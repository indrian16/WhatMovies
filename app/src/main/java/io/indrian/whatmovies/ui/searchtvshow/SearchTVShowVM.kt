package io.indrian.whatmovies.ui.searchtvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.github.ajalt.timberkt.d
import io.indrian.whatmovies.data.repositories.Repository
import io.indrian.whatmovies.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchTVShowVM(
    handle: SavedStateHandle,
    private val repository: Repository
) : BaseViewModel() {

    private val mutSearchTVShowState = MutableLiveData<SearchTVShowState>()
    val searchTVShowState: LiveData<SearchTVShowState>
        get() = mutSearchTVShowState

    init {

        mutSearchTVShowState.value = SearchTVShowState.InitState
    }

    fun searchTVShow(query: String) {

        val disposable = repository.getSearchTVShow(query, getLanguage())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mutSearchTVShowState.value = SearchTVShowState.Loading }
            .subscribe(

                { tvShowList ->

                    d { "TVShowList: ${tvShowList.size}" }
                    if (tvShowList.isNotEmpty()) {

                        mutSearchTVShowState.value = SearchTVShowState.Loaded(tvShowList)
                    } else {

                        mutSearchTVShowState.value = SearchTVShowState.NotFound
                    }
                },
                { error ->

                    val errorMessage = error.message.toString()
                    d { "error: $errorMessage" }
                    mutSearchTVShowState.value = SearchTVShowState.Error(errorMessage)
                }
            )

        compositeDisposable.add(disposable)
    }
}