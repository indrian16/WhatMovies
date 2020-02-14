package io.indrian.moviecatalogue.data.service

import io.indrian.moviecatalogue.data.response.ResponseMovie
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverService {

    @GET("discover/movie/")
    fun getLatestMovieToday(
        @Query("primary_release_date.gte") dateGte: String,
        @Query("primary_release_date.lte") dateLte: String
    ): Single<ResponseMovie>
}