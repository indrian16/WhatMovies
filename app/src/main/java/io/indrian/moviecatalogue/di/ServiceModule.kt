package io.indrian.moviecatalogue.di

import dagger.Module
import dagger.Provides
import io.indrian.moviecatalogue.data.service.MovieService
import io.indrian.moviecatalogue.data.service.TVShowService
import retrofit2.Retrofit

@Module
class ServiceModule {

    @Provides
    fun provideMovieService(retrofit: Retrofit) = retrofit.create(MovieService::class.java)

    @Provides
    fun provideTVShowService(retrofit: Retrofit) = retrofit.create(TVShowService::class.java)
}