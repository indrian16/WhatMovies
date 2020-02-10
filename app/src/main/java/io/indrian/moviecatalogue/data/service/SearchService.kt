package io.indrian.moviecatalogue.data.service

import io.indrian.moviecatalogue.data.response.ResponseMovie
import io.indrian.moviecatalogue.data.response.ResponseTVShow
import io.reactivex.Observable
import retrofit2.http.GET

interface SearchService {

    @GET("search/movie")
    fun getSearchMovie(query: String): Observable<ResponseMovie>

    @GET("search/tv")
    fun getSearchTVShow(query: String): Observable<ResponseTVShow>
}