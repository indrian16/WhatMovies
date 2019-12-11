package io.indrian.moviecatalogue.data.repositories

import io.indrian.moviecatalogue.data.model.Movie
import io.indrian.moviecatalogue.data.service.MovieService

class Repository(private val movieService: MovieService) {

    fun getMovies() = movieService.getMovies()
        .flatMapIterable { it }
        .map { mapImagePath(it) }
        .toList()!!

    private fun mapImagePath(movie: Movie) = Movie(
        id = movie.id,
        title = movie.title,
        poster = "https://image.tmdb.org/t/p/w342"+movie.poster,
        backdrop = "https://image.tmdb.org/t/p/w780"+movie.backdrop,
        year = movie.year,
        overview = movie.overview
    )
}