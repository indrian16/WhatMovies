package io.indrian.whatmovies.ui.favoritemovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.github.ajalt.timberkt.Timber.d
import io.indrian.whatmovies.data.repositories.Repository
import io.indrian.whatmovies.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FavoriteMovieVM(
    val handle: SavedStateHandle,
    private val repository: Repository
) : BaseViewModel() {

    private val mutFavoriteMovieListState = MutableLiveData<FavoriteMovieListState>()
    val favoriteMovieListState: LiveData<FavoriteMovieListState>
        get() = mutFavoriteMovieListState

    fun getFavoriteMovies() {

        val disposable = repository.getFavoriteMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mutFavoriteMovieListState.value = FavoriteMovieListState.Loading }
            .subscribe(
                { movie ->

                    if (movie.isNotEmpty()) {

                        d { "Movie: ${movie.size}" }
                        mutFavoriteMovieListState.value = FavoriteMovieListState.Loaded(movie)
                    } else {

                        mutFavoriteMovieListState.value = FavoriteMovieListState.Empty
                    }
                },
                {error ->

                    error.printStackTrace()
                    val errorMessage = error.message.toString()
                    d { "Error: $errorMessage" }
                    mutFavoriteMovieListState.value = FavoriteMovieListState.Error(errorMessage)
                }
            )

        compositeDisposable.add(disposable)
    }
}