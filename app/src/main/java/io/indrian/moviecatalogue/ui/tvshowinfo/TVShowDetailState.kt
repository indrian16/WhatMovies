package io.indrian.moviecatalogue.ui.tvshowinfo

import io.indrian.moviecatalogue.data.model.TVShowDetail

sealed class TVShowDetailState {

    object Loading: TVShowDetailState()
    data class Error(val message: String): TVShowDetailState()
    data class Loaded(val tvShowDetail: TVShowDetail): TVShowDetailState()
}