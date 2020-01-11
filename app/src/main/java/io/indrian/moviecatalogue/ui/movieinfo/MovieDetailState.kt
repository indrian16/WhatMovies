package io.indrian.moviecatalogue.ui.movieinfo

import io.indrian.moviecatalogue.data.model.MovieDetail

sealed class MovieDetailState {

    object Loading: MovieDetailState()
    data class Error(val message: String): MovieDetailState()
    data class Loaded(val movieDetail: MovieDetail): MovieDetailState()
}