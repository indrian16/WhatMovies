package io.indrian.moviecatalogue.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import io.indrian.moviecatalogue.ui.main.MainActivity
import io.indrian.moviecatalogue.ui.movie.MovieFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    MapperModule::class,
    RepositoryModule::class
])
interface AppComponent {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: MovieFragment)
}