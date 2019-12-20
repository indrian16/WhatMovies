package io.indrian.moviecatalogue.ui.settings

sealed class LanguageState {

    object English : LanguageState()
    object Indonesian : LanguageState()
}