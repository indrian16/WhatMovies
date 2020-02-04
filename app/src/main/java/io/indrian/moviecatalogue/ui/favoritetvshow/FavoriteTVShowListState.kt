package io.indrian.moviecatalogue.ui.favoritetvshow

import io.indrian.moviecatalogue.data.model.TVShow

sealed class FavoriteTVShowListState {

    object Loading: FavoriteTVShowListState()
    data class Error(val message: String): FavoriteTVShowListState()
    data class Loaded(
        val tvShowList: List<TVShow>
    ): FavoriteTVShowListState()
    object Empty : FavoriteTVShowListState()
}