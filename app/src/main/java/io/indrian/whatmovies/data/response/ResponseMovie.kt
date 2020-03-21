package io.indrian.whatmovies.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.indrian.whatmovies.data.entity.MovieEntity

@JsonClass(generateAdapter = true)
data class ResponseMovie(
    @Json(name = "page")
    var page: Int?,
    @Json(name = "results")
    var results: List<MovieEntity> = arrayListOf(),
    @Json(name = "total_pages")
    var totalPages: Int?,
    @Json(name = "total_results")
    var totalResults: Int?
)