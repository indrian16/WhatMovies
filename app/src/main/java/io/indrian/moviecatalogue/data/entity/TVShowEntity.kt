package io.indrian.moviecatalogue.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TVShowEntity(
    @Json(name = "backdrop_path")
    var backdropPath: String?,
    @Json(name = "first_air_date")
    var firstAirDate: String?,
    @Json(name = "genre_ids")
    var genreIds: List<Int?>?,
    @Json(name = "id")
    var id: Int?,
    @Json(name = "name")
    var name: String?,
    @Json(name = "origin_country")
    var originCountry: List<String?>?,
    @Json(name = "original_language")
    var originalLanguage: String?,
    @Json(name = "original_name")
    var originalName: String?,
    @Json(name = "overview")
    var overview: String?,
    @Json(name = "popularity")
    var popularity: Double?,
    @Json(name = "poster_path")
    var posterPath: String?,
    @Json(name = "vote_average")
    var voteAverage: Double?,
    @Json(name = "vote_count")
    var voteCount: Int?
)