package io.indrian.moviecatalogue.ui.settings

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yariksoffice.lingver.Lingver
import io.indrian.moviecatalogue.ui.base.BaseViewModel
import io.indrian.moviecatalogue.utils.Constant

class SettingsVM : BaseViewModel() {

    private val mutLanguageState = MutableLiveData<LanguageState>()
    val languageState: LiveData<LanguageState>
        get() = mutLanguageState

    fun getLanguageSettings() {

        when (getLanguageSetting()) {

            Constant.DEFAULT_LANGUAGE -> {

                mutLanguageState.value = LanguageState.English
            }

            Constant.ID -> {

                mutLanguageState.value = LanguageState.Indonesian
            }
        }
    }

    fun changeLanguage(context: Context, language: String) {

        Lingver.getInstance().setLocale(context, language)
    }
}