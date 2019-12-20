package io.indrian.moviecatalogue.ui.tvshow

import io.indrian.moviecatalogue.data.model.TVShow

sealed class TVShowListState {

    object Loading: TVShowListState()
    data class Error(val message: String): TVShowListState()
    data class Loaded(val tvShows: List<TVShow>): TVShowListState()
}