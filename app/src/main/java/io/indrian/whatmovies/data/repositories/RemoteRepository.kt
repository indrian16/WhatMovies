package io.indrian.whatmovies.data.repositories

import io.indrian.whatmovies.data.mapper.MovieDetailMapper
import io.indrian.whatmovies.data.mapper.MovieMapper
import io.indrian.whatmovies.data.mapper.TVShowDetailMapper
import io.indrian.whatmovies.data.mapper.TVShowMapper
import io.indrian.whatmovies.data.model.Movie
import io.indrian.whatmovies.data.model.MovieDetail
import io.indrian.whatmovies.data.model.TVShow
import io.indrian.whatmovies.data.model.TVShowDetail
import io.indrian.whatmovies.data.service.DiscoverService
import io.indrian.whatmovies.data.service.MovieService
import io.indrian.whatmovies.data.service.SearchService
import io.indrian.whatmovies.data.service.TVShowService
import io.reactivex.Observable
import io.reactivex.Single

class RemoteRepository(
    private val movieService: MovieService,
    private val tvShowService: TVShowService,
    private val searchService: SearchService,
    private val discoverService: DiscoverService,
    private val movieMapper: MovieMapper,
    private val tvShowMapper: TVShowMapper,
    private val tvShowDetailMapper: TVShowDetailMapper,
    private val movieDetailMapper: MovieDetailMapper
) {

    fun getMovies(language: String): Observable<List<Movie>> =

        movieService.getMovies(language)
            .flatMapIterable { it.results }
            .map { movieMapper.toModel(it) }
            .toList()
            .toObservable()

    fun getTVShow(language: String): Observable<List<TVShow>> =

        tvShowService.getTVShow(language)
            .flatMapIterable { it.results }
            .map { tvShowMapper.toModel(it) }
            .toList()
            .toObservable()!!

    fun getTVShowDetail(id: Int, language: String): Observable<TVShowDetail> =

        tvShowService.getDetailTVShow(id, language)
            .map { tvShowDetailMapper.toModel(it) }

    fun getMovieDetail(id: Int, language: String): Observable<MovieDetail> =

        movieService.getMovieDetail(id, language)
            .map { movieDetailMapper.toModel(it) }

    fun getSearchMovie(query: String, language: String): Observable<List<Movie>> =

        searchService.getSearchMovie(query, language)
            .flatMapIterable { it.results }
            .map { movieMapper.toModel(it) }
            .toList()
            .toObservable()

    fun getSearchTVShow(query: String, language: String): Observable<List<TVShow>> =

        searchService.getSearchTVShow(query, language)
            .flatMapIterable { it.results }
            .map { tvShowMapper.toModel(it) }
            .toList()
            .toObservable()

    fun getLatestMovieToday(dateGte: String, dateLte: String): Single<List<Movie>> =

        discoverService.getLatestMovieToday(dateGte, dateLte)
            .flattenAsObservable { it.results }
            .map { movieMapper.toModel(it) }
            .toList()
}