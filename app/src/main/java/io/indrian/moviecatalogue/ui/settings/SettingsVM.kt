package io.indrian.moviecatalogue.ui.settings

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yariksoffice.lingver.Lingver
import io.indrian.moviecatalogue.utils.Constant

class SettingsVM : ViewModel() {

    private val lingver = Lingver.getInstance()

    private val mutLanguageState = MutableLiveData<LanguageState>()
    val languageState: LiveData<LanguageState>
        get() = mutLanguageState

    fun getLanguageSettings() {

        val language = lingver.getLanguage()
        setLanguageState(language)
    }

    fun changeLanguage(context: Context, language: String) {

        lingver.setLocale(context, language)
    }

    private fun setLanguageState(language: String) {

        when (language) {

            Constant.DEFAULT_LANGUAGE -> {

                mutLanguageState.value = LanguageState.English
            }

            Constant.ID -> {

                mutLanguageState.value = LanguageState.Indonesian
            }
        }
    }

}