package io.indrian.whatmovies.ui.movieinfo

import io.indrian.whatmovies.data.model.MovieDetail

sealed class MovieDetailState {

    object Loading: MovieDetailState()
    data class Error(val message: String): MovieDetailState()
    data class Loaded(val movieDetail: MovieDetail): MovieDetailState()
}