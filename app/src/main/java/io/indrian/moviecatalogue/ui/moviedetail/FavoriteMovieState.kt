package io.indrian.moviecatalogue.ui.moviedetail

sealed class FavoriteMovieState {

    data class Exist(val action: Boolean) : FavoriteMovieState()
    data class NotExist(val action: Boolean) : FavoriteMovieState()
    data class Error(val message: String) : FavoriteMovieState()
}