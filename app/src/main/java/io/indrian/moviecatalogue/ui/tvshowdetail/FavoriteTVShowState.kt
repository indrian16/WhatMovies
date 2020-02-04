package io.indrian.moviecatalogue.ui.tvshowdetail

sealed class FavoriteTVShowState {

    data class Exist(val action: Boolean) : FavoriteTVShowState()
    data class NotExist(val action: Boolean) : FavoriteTVShowState()
    data class Error(val message: String) : FavoriteTVShowState()
}