package io.indrian.whatmovies.ui.favoritemovie

import io.indrian.whatmovies.data.model.Movie

sealed class FavoriteMovieListState {

    object Loading: FavoriteMovieListState()
    data class Error(val message: String): FavoriteMovieListState()
    data class Loaded(
        val movies: List<Movie>
    ): FavoriteMovieListState()
    object Empty : FavoriteMovieListState()
}