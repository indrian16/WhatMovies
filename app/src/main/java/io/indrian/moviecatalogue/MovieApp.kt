package io.indrian.moviecatalogue

import android.app.Application
import com.github.ajalt.timberkt.Timber
import com.yariksoffice.lingver.Lingver
import io.indrian.moviecatalogue.di.*
import io.indrian.moviecatalogue.utils.Constant
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 *  @author: INDRIAN(rg16)
 *  @github: https://github.com/indrian16
 *  @dicoding: https://www.dicoding.com/users/229215
 *  @submission: 3
 *  @repository: https://github.com/indrian16/MovieCatalogClean
 * */

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Lingver.init(this, Constant.DEFAULT_LANGUAGE)

        if (BuildConfig.DEBUG) {

            Timber.plant(Timber.DebugTree())
        }

        startKoin {

            androidLogger(Level.DEBUG)
            androidContext(applicationContext)
            modules(listOf(
                dbModule,
                networkModule,
                serviceModule,
                mapperModule,
                repoModule,
                appModule
            ))
        }
    }
}