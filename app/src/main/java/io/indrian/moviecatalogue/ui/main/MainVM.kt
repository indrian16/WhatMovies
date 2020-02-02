package io.indrian.moviecatalogue.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.hadilq.liveevent.LiveEvent
import io.indrian.moviecatalogue.ui.base.BaseViewModel

class MainVM(val handle: SavedStateHandle) : BaseViewModel() {

    private val mutMenuState = LiveEvent<MenuState>()
    val menuState: LiveData<MenuState>
        get() = mutMenuState

    fun initState() {

        mutMenuState.value = MenuState.Movie
    }

    fun changeMenu(menuState: MenuState) {

        mutMenuState.value = menuState
    }
}