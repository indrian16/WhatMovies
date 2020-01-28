package io.indrian.moviecatalogue.di

import dagger.Module
import dagger.Provides
import io.indrian.moviecatalogue.data.mapper.MovieDetailMapper
import io.indrian.moviecatalogue.data.mapper.MovieMapper
import io.indrian.moviecatalogue.data.mapper.TVShowDetailMapper
import io.indrian.moviecatalogue.data.mapper.TVShowMapper
import io.indrian.moviecatalogue.data.repositories.Repository
import io.indrian.moviecatalogue.data.service.MovieService
import io.indrian.moviecatalogue.data.service.TVShowService
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        movieService: MovieService,
        tvShowService: TVShowService,
        movieMapper: MovieMapper,
        tvShowMapper: TVShowMapper,
        tvShowDetailMapper: TVShowDetailMapper,
        movieDetailMapper: MovieDetailMapper
    ): Repository {

        return Repository(
            movieService,
            tvShowService,
            movieMapper,
            tvShowMapper,
            tvShowDetailMapper,
            movieDetailMapper
        )
    }
}