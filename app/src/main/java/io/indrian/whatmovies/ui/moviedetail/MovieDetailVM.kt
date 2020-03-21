package io.indrian.whatmovies.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import io.indrian.whatmovies.data.model.Movie
import io.indrian.whatmovies.data.repositories.Repository
import io.indrian.whatmovies.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailVM(
    val handle: SavedStateHandle,
    private val repository: Repository
) : BaseViewModel() {

    private val mutMovieGenreState = MutableLiveData<MovieGenreState>()
    val movieGenreState: LiveData<MovieGenreState>
        get() = mutMovieGenreState

    private val mutFavoriteMovieState = MutableLiveData<FavoriteMovieState>()
    val favoriteMovieState: LiveData<FavoriteMovieState>
        get() = mutFavoriteMovieState

    init {

        mutFavoriteMovieState.value = FavoriteMovieState.NotExist(false)
    }

    fun getMovieGenres(id: Int) {

        val disposable = repository.getMovieDetail(id, getLanguage())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { mutMovieGenreState.value = MovieGenreState.Loading }
            .subscribe(

                { movieDetail ->

                    d { "Genres: ${movieDetail.genres}" }
                    mutMovieGenreState.value = MovieGenreState.Loaded(movieDetail.genres)
                },
                { error ->

                    error.printStackTrace()
                    e { "Error: $error" }
                    mutMovieGenreState.value = MovieGenreState.Error(error.message!!)
                }
            )

        compositeDisposable.add(disposable)
    }

    fun getMoviesIsExist(id: Int) {

        val disposable = repository.getFavoriteMovieIsExit(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { exist ->

                    if (exist) {

                        mutFavoriteMovieState.value = FavoriteMovieState.Exist(false)
                    } else {

                        mutFavoriteMovieState.value = FavoriteMovieState.NotExist(false)
                    }
                },
                { error ->

                    error.printStackTrace()
                    val errorMessage = error.message.toString()
                    d { errorMessage }
                    mutFavoriteMovieState.value = FavoriteMovieState.Error(errorMessage)
                }
            )

        compositeDisposable.add(disposable)
    }

    fun addFavorite(movie: Movie) {

        val disposable = repository.addFavoriteMovie(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    d { "Add id: $it" }
                    mutFavoriteMovieState.value = FavoriteMovieState.Exist(true)
                },
                { error ->

                    error.printStackTrace()
                    val errorMessage = error.message.toString()
                    d { errorMessage }
                    mutFavoriteMovieState.value = FavoriteMovieState.Error(errorMessage)
                }
            )

        compositeDisposable.add(disposable)
    }

    fun delFavorite(movie: Movie) {

        val disposable = repository.deleteFavoriteMovie(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    d { "Delete Status: $it" }
                    mutFavoriteMovieState.value = FavoriteMovieState.NotExist(true)
                },
                { error ->

                    error.printStackTrace()
                    val errorMessage = error.message.toString()
                    d { errorMessage }
                    mutFavoriteMovieState.value = FavoriteMovieState.Error(errorMessage)
                }
            )

        compositeDisposable.add(disposable)
    }
}