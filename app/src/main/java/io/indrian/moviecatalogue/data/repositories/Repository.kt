package io.indrian.moviecatalogue.data.repositories

import io.indrian.moviecatalogue.data.model.Movie
import io.indrian.moviecatalogue.data.model.TVShow
import io.indrian.moviecatalogue.data.service.MovieService
import io.indrian.moviecatalogue.data.service.TVShowService

class Repository(private val movieService: MovieService,
                 private val tvShowService: TVShowService) {

    fun getMovies() = movieService.getMovies()
        .flatMapIterable { it }
        .map { mapMovieImagePath(it) }
        .toList()!!

    fun getTVShows() = tvShowService.getTVShows()
        .flatMapIterable { it }
        .map { mapTVImagePath(it) }
        .toList()!!

    private fun mapMovieImagePath(movie: Movie) = Movie(
        id = movie.id,
        title = movie.title,
        poster = "https://image.tmdb.org/t/p/w342"+movie.poster,
        backdrop = "https://image.tmdb.org/t/p/w780"+movie.backdrop,
        year = movie.year,
        overview = movie.overview
    )

    private fun mapTVImagePath(tvShow: TVShow) = TVShow(
        id = tvShow.id,
        name = tvShow.name,
        poster = "https://image.tmdb.org/t/p/w342"+tvShow.poster,
        backdrop = "https://image.tmdb.org/t/p/w780"+tvShow.backdrop,
        year = tvShow.year,
        overview = tvShow.overview
    )
}