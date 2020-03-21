package io.indrian.whatmovies.ui.settings

sealed class LanguageState {

    object English : LanguageState()
    object Indonesian : LanguageState()
}