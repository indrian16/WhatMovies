package io.indrian.moviecatalogue.di

import io.indrian.moviecatalogue.data.repositories.Repository
import io.indrian.moviecatalogue.data.service.MovieService
import io.indrian.moviecatalogue.ui.movie.MovieVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val serviceModule = module {

    single { MovieService() }
}

val repoModule = module {

    single { Repository(get()) }
}

val appModule = module {

    viewModel { MovieVM(get()) }
}
