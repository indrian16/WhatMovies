package io.indrian.whatmovies.ui.tvshowinfo

import io.indrian.whatmovies.data.model.TVShowDetail

sealed class TVShowDetailState {

    object Loading: TVShowDetailState()
    data class Error(val message: String): TVShowDetailState()
    data class Loaded(val tvShowDetail: TVShowDetail): TVShowDetailState()
}