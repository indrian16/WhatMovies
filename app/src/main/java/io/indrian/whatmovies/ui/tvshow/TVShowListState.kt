package io.indrian.whatmovies.ui.tvshow

import io.indrian.whatmovies.data.model.TVShow

sealed class TVShowListState {

    object Loading: TVShowListState()
    data class Error(val message: String): TVShowListState()
    data class Loaded(val tvShows: List<TVShow>): TVShowListState()
}