package io.indrian.moviecatalogue

import android.app.Application
import com.github.ajalt.timberkt.Timber
import io.indrian.moviecatalogue.di.appModule
import io.indrian.moviecatalogue.di.repoModule
import io.indrian.moviecatalogue.di.serviceModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 *
 *  @author: INDRIAN(rg16)
 *  @github: https://github.com/indrian16
 *  @dicoding: https://www.dicoding.com/users/229215
 * */

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {

            Timber.plant(Timber.DebugTree())
        }

        startKoin {

            androidLogger(Level.DEBUG)
            modules(listOf(
                serviceModule,
                repoModule,
                appModule
            ))
        }
    }
}