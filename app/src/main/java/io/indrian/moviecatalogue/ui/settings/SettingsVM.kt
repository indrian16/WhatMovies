package io.indrian.moviecatalogue.ui.settings

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yariksoffice.lingver.Lingver
import io.indrian.moviecatalogue.notification.DailyMovieReceiver
import io.indrian.moviecatalogue.notification.LatestMovieTodayReceiver
import io.indrian.moviecatalogue.ui.base.BaseViewModel
import io.indrian.moviecatalogue.utils.Constant

class SettingsVM(private val context: Context) : BaseViewModel() {

    private val mutLanguageState = MutableLiveData<LanguageState>()
    val languageState: LiveData<LanguageState>
        get() = mutLanguageState

    private val mutDailyRemainder = MutableLiveData<Boolean>()
    val dailyRemainder: LiveData<Boolean>
        get() = mutDailyRemainder

    private val mutDailyLatestMovie = MutableLiveData<Boolean>()
    val dailyLatestMovie: LiveData<Boolean>
        get() = mutDailyLatestMovie

    private val dailyMovieReceiver = DailyMovieReceiver()
    private val latestMovieTodayReceiver = LatestMovieTodayReceiver()

    private val sharedPref = context.getSharedPreferences(Constant.SETTING_KEY_FILE, Context.MODE_PRIVATE)

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

    fun getSetting() {

        mutDailyRemainder.value = sharedPref.getBoolean(Constant.DAILY_OPEN_APP, false)
        mutDailyLatestMovie.value = sharedPref.getBoolean(Constant.DAILY_LETEST_MOVIE, false)
    }

    fun setDailyRemainder(state: Boolean) {

        val edit = sharedPref.edit()

        if (state) {

            edit.putBoolean(Constant.DAILY_OPEN_APP, true).apply()
            dailyMovieReceiver.startDaily(context)
        } else {

            edit.putBoolean(Constant.DAILY_OPEN_APP, false).apply()
            dailyMovieReceiver.stopDaily()
        }
    }

    fun setLatestMovie(state: Boolean) {

        val edit = sharedPref.edit()

        if (state) {

            edit.putBoolean(Constant.DAILY_LETEST_MOVIE, true).apply()
            latestMovieTodayReceiver.onSubscribe(context)
        } else {

            edit.putBoolean(Constant.DAILY_LETEST_MOVIE, false).apply()
            latestMovieTodayReceiver.unSubscribe()
        }
    }
}