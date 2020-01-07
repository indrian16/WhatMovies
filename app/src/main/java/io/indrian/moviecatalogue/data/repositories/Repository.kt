package io.indrian.moviecatalogue.data.repositories

import io.indrian.moviecatalogue.data.mapper.MovieMapper
import io.indrian.moviecatalogue.data.mapper.TVShowMapper
import io.indrian.moviecatalogue.data.model.Movie
import io.indrian.moviecatalogue.data.model.TVShow
import io.indrian.moviecatalogue.data.service.MovieService
import io.indrian.moviecatalogue.data.service.TVShowService
import io.reactivex.Observable

class Repository(private val movieService: MovieService,
                 private val tvShowService: TVShowService,
                 private val movieMapper: MovieMapper,
                 private val tvShowMapper: TVShowMapper) {

    fun getMovies(language: String): Observable<MutableList<Movie>> =

        movieService.getMovies(language)
            .flatMapIterable { it.results!! }
            .map { movieMapper.toModel(it) }
            .toList()
            .toObservable()

    fun getTVShow(language: String): Observable<MutableList<TVShow>> =

        tvShowService.getTVShow(language)
            .flatMapIterable { it.results!! }
            .map { tvShowMapper.toModel(it) }
            .toList()
            .toObservable()!!
}