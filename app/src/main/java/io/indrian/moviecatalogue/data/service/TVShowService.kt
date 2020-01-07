package io.indrian.moviecatalogue.data.service

import io.indrian.moviecatalogue.data.response.ResponseTVShow
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface TVShowService {

    /**
     *
     *  @param language type String
     *  sample parameter: en-US, id-ID
     *  @return Single<ResponseTVShow> -> TVShowEntity -> TVShow
     * */
    @GET("3/tv/airing_today")
    fun getTVShow(@Query("language") language: String): Observable<ResponseTVShow>
}