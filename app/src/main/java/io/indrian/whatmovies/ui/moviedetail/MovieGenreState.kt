package io.indrian.whatmovies.ui.moviedetail

import io.indrian.whatmovies.data.model.Genre

sealed class MovieGenreState {

    object Loading : MovieGenreState()
    data class Error(val message: String): MovieGenreState()
    data class Loaded(val genres: List<Genre>): MovieGenreState()
}