package io.indrian.moviecatalogue.di

import io.indrian.moviecatalogue.data.repositories.Repository
import io.indrian.moviecatalogue.data.service.MovieService
import io.indrian.moviecatalogue.data.service.TVShowService
import io.indrian.moviecatalogue.ui.movie.MovieVM
import io.indrian.moviecatalogue.ui.settings.SettingsVM
import io.indrian.moviecatalogue.ui.tvshow.TVShowVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val serviceModule = module {

    single { MovieService() }
    single { TVShowService() }
}

val repoModule = module {

    single { Repository(get(), get()) }
}

val appModule = module {

    viewModel { MovieVM(get()) }
    viewModel { TVShowVM(get()) }
    viewModel { SettingsVM() }
}
