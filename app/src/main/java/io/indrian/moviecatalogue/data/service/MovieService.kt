package io.indrian.moviecatalogue.data.service

import io.indrian.moviecatalogue.data.response.ResponseMovie
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    /**
     *
     *  @param language type String
     *  sample parameter: en-US, id-ID
     *  @return Single<ResponseMovie> -> MovieEntity -> Movie
     * */
    @GET("3/movie/popular")
    fun getMovies(@Query("language") language: String): Observable<ResponseMovie>
}