package io.indrian.moviecatalogue.data.service

import io.indrian.moviecatalogue.data.entity.DetailTVShowEntity
import io.indrian.moviecatalogue.data.response.ResponseTVShow
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TVShowService {

    /**
     *
     *  @param language type String
     *  sample parameter: en-US, id-ID
     *  @return Observable<ResponseTVShow> -> TVShowEntity -> TVShow
     * */
    @GET("tv/airing_today")
    fun getTVShow(@Query("language") language: String): Observable<ResponseTVShow>

    /**
     *
     *  @param id type Int
     *  @param language type String
     *  sample parameter: en-US, id-ID
     *  @return Observable<DetailTVShowEntity> -> TVShowDetail
     * */
    @GET("tv/{id}")
    fun getDetailTVShow(
        @Path("id") id: Int,
        @Query("language") language: String
    ): Observable<DetailTVShowEntity>
}