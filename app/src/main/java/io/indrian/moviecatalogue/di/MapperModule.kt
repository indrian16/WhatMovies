package io.indrian.moviecatalogue.di

import dagger.Module
import dagger.Provides
import io.indrian.moviecatalogue.data.mapper.MovieDetailMapper
import io.indrian.moviecatalogue.data.mapper.MovieMapper
import io.indrian.moviecatalogue.data.mapper.TVShowDetailMapper
import io.indrian.moviecatalogue.data.mapper.TVShowMapper

@Module
class MapperModule {

    @Provides
    fun provideMovieMapper() = MovieMapper()

    @Provides
    fun provideTVShowMapper() = TVShowMapper()

    @Provides
    fun provideMovieDetailMapper() = MovieDetailMapper()

    @Provides
    fun provideTVShowDetailMapper() = TVShowDetailMapper()
}