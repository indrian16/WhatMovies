package io.indrian.moviecatalogue.ui.tvshowdetail

import io.indrian.moviecatalogue.data.model.Genre

sealed class GenreTVShowState {

    object Loading : GenreTVShowState()
    data class Loaded(val genres: List<Genre>) : GenreTVShowState()
    data class Error(val message: String): GenreTVShowState()
}