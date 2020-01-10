package io.indrian.moviecatalogue.data.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailEntity(
    @Json(name = "adult")
    var adult: Boolean?,
    @Json(name = "backdrop_path")
    var backdropPath: String?,
    @Json(name = "budget")
    var budget: Int?,
    @Json(name = "genres")
    var genres: List<GenreEntity?>?,
    @Json(name = "homepage")
    var homepage: String?,
    @Json(name = "id")
    var id: Int?,
    @Json(name = "imdb_id")
    var imdbId: String?,
    @Json(name = "original_language")
    var originalLanguage: String?,
    @Json(name = "original_title")
    var originalTitle: String?,
    @Json(name = "overview")
    var overview: String?,
    @Json(name = "popularity")
    var popularity: Double?,
    @Json(name = "poster_path")
    var posterPath: String?,
    @Json(name = "release_date")
    var releaseDate: String?,
    @Json(name = "revenue")
    var revenue: Int?,
    @Json(name = "runtime")
    var runtime: Int?,
    @Json(name = "status")
    var status: String?,
    @Json(name = "tagline")
    var tagline: String?,
    @Json(name = "title")
    var title: String?,
    @Json(name = "video")
    var video: Boolean?,
    @Json(name = "vote_average")
    var voteAverage: Double?,
    @Json(name = "vote_count")
    var voteCount: Int?
)