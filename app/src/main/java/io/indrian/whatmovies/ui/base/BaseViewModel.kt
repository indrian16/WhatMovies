package io.indrian.whatmovies.ui.base

import androidx.lifecycle.ViewModel
import com.yariksoffice.lingver.Lingver
import io.indrian.whatmovies.utils.Constant
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()
    private val lingver = Lingver.getInstance()


    protected fun getLanguage(): String {

        return when (lingver.getLanguage()) {

            Constant.DEFAULT_LANGUAGE -> Constant.EN_US
            Constant.ID -> Constant.ID_ID
            else -> Constant.EN_US
        }
    }

    protected fun getLanguageSetting() = lingver.getLanguage()

    override fun onCleared() {

        compositeDisposable.clear()
        compositeDisposable.dispose()
        super.onCleared()
    }
}