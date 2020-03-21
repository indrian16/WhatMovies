package io.indrian.whatmovies.ui.searchtvshow

import io.indrian.whatmovies.data.model.TVShow

sealed class SearchTVShowState {

    object InitState : SearchTVShowState()
    object Loading: SearchTVShowState()
    data class Error(val message: String): SearchTVShowState()
    data class Loaded(
        val tvShowList: List<TVShow>
    ): SearchTVShowState()
    object NotFound : SearchTVShowState()
}