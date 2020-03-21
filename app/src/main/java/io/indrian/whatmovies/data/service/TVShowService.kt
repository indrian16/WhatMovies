package io.indrian.whatmovies.data.service

import io.indrian.whatmovies.data.entity.DetailTVShowEntity
import io.indrian.whatmovies.data.response.ResponseTVShow
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