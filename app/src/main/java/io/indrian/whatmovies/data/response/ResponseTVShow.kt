package io.indrian.whatmovies.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.indrian.whatmovies.data.entity.TVShowEntity

@JsonClass(generateAdapter = true)
data class ResponseTVShow(
    @Json(name = "page")
    var page: Int?,
    @Json(name = "results")
    var results: List<TVShowEntity> = arrayListOf(),
    @Json(name = "total_pages")
    var totalPages: Int?,
    @Json(name = "total_results")
    var totalResults: Int?
)