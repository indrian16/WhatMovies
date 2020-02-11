package io.indrian.moviecatalogue.data.service

import io.indrian.moviecatalogue.data.response.ResponseMovie
import io.indrian.moviecatalogue.data.response.ResponseTVShow
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search/movie")
    fun getSearchMovie(
        @Query("query") query: String,
        @Query("language") language: String
    ): Observable<ResponseMovie>

    @GET("search/tv")
    fun getSearchTVShow(
        @Query("query") query: String,
        @Query("language") language: String
    ): Observable<ResponseTVShow>
}