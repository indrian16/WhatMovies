package io.indrian.moviecatalogue.ui.searchtvshow

import io.indrian.moviecatalogue.data.model.TVShow

sealed class SearchTVShowState {

    object InitState : SearchTVShowState()
    object Loading: SearchTVShowState()
    data class Error(val message: String): SearchTVShowState()
    data class Loaded(
        val tvShowList: List<TVShow>
    ): SearchTVShowState()
    object NotFound : SearchTVShowState()
}