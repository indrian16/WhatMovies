package io.indrian.moviecatalogue.data.repositories

import io.indrian.moviecatalogue.data.db.dao.MovieDao
import io.indrian.moviecatalogue.data.db.dao.TVShowDao
import io.indrian.moviecatalogue.data.model.Movie
import io.indrian.moviecatalogue.data.model.TVShow

class LocalRepository(
    private val movieDao: MovieDao,
    private val tvShowDao: TVShowDao
) {

    fun getMovies() = movieDao.getAllMovies()

    fun addAllMovie(movies: List<Movie>) = movieDao.insertAllMovie(movies)

    fun getTVShowList() = tvShowDao.getAllTVShowList()

    fun addAllTVShow(tvShowList: List<TVShow>) = tvShowDao.insetAllTVShowList(tvShowList)
}