package io.indrian.moviecatalogue.ui.favoritemovie

import io.indrian.moviecatalogue.data.model.Movie

sealed class FavoriteMovieListState {

    object Loading: FavoriteMovieListState()
    data class Error(val message: String): FavoriteMovieListState()
    data class Loaded(
        val movies: List<Movie>
    ): FavoriteMovieListState()
    object Empty : FavoriteMovieListState()
}