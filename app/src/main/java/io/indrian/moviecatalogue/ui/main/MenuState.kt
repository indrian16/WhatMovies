package io.indrian.moviecatalogue.ui.main

sealed class MenuState {

    object Movie : MenuState()
    object TVShow : MenuState()
    object Discover : MenuState()
}