package io.indrian.whatmovies.ui.favoritetvshow

import io.indrian.whatmovies.data.model.TVShow

sealed class FavoriteTVShowListState {

    object Loading: FavoriteTVShowListState()
    data class Error(val message: String): FavoriteTVShowListState()
    data class Loaded(
        val tvShowList: List<TVShow>
    ): FavoriteTVShowListState()
    object Empty : FavoriteTVShowListState()
}