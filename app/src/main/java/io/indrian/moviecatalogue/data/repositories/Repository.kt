package io.indrian.moviecatalogue.data.repositories

import io.indrian.moviecatalogue.data.mapper.DetailTVShowMapper
import io.indrian.moviecatalogue.data.mapper.MovieDetailMapper
import io.indrian.moviecatalogue.data.mapper.MovieMapper
import io.indrian.moviecatalogue.data.mapper.TVShowMapper
import io.indrian.moviecatalogue.data.model.DetailTVShow
import io.indrian.moviecatalogue.data.model.Movie
import io.indrian.moviecatalogue.data.model.MovieDetail
import io.indrian.moviecatalogue.data.model.TVShow
import io.indrian.moviecatalogue.data.service.MovieService
import io.indrian.moviecatalogue.data.service.TVShowService
import io.reactivex.Observable

class Repository(private val movieService: MovieService,
                 private val tvShowService: TVShowService,
                 private val movieMapper: MovieMapper,
                 private val tvShowMapper: TVShowMapper,
                 private val detailTVShowMapper: DetailTVShowMapper,
                 private val movieDetailMapper: MovieDetailMapper) {

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

    fun getDetailTVShow(id: Int, language: String): Observable<DetailTVShow> =

        tvShowService.getDetailTVShow(id, language)
            .map { detailTVShowMapper.toModel(it) }

    fun getMovieDetail(id: Int, language: String): Observable<MovieDetail> =

        movieService.getMovieDetail(id, language)
            .map { movieDetailMapper.toModel(it) }
}