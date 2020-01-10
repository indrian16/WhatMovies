package io.indrian.moviecatalogue.data.service

import io.indrian.moviecatalogue.data.entity.MovieDetailEntity
import io.indrian.moviecatalogue.data.response.ResponseMovie
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    /**
     *
     *  @param language type String
     *  sample parameter: en-US, id-ID
     *  @return Single<ResponseMovie> -> MovieEntity -> Movie
     * */
    @GET("movie/popular")
    fun getMovies(@Query("language") language: String): Observable<ResponseMovie>

    /**
     *
     *  @param id type Int
     *  @param language type String
     *  sample parameter: en-US, id-ID
     *  @return Observable<MovieDetailEntity> -> MovieDetail
     * */
    @GET("movie/{id}")
    fun getMovieDetail(
        @Path("id") id: Int,
        @Query("language") language: String
    ): Observable<MovieDetailEntity>
}