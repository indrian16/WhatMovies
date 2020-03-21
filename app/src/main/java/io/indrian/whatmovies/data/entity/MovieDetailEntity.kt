package io.indrian.whatmovies.data.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailEntity(
    @Json(name = "adult")
    var adult: Boolean = false,
    @Json(name = "backdrop_path")
    var backdropPath: String = "",
    @Json(name = "budget")
    var budget: Int = 0,
    @Json(name = "genres")
    var genres: List<GenreEntity> = arrayListOf(),
    @Json(name = "homepage")
    var homepage: String = "",
    @Json(name = "id")
    var id: Int = 0,
    @Json(name = "imdb_id")
    var imdbId: String = "",
    @Json(name = "original_language")
    var originalLanguage: String = "",
    @Json(name = "original_title")
    var originalTitle: String = "",
    @Json(name = "overview")
    var overview: String = "",
    @Json(name = "popularity")
    var popularity: Double = 0.0,
    @Json(name = "poster_path")
    var posterPath: String = "",
    @Json(name = "release_date")
    var releaseDate: String = "",
    @Json(name = "revenue")
    var revenue: Int = 0,
    @Json(name = "runtime")
    var runtime: Int = 0,
    @Json(name = "status")
    var status: String = "",
    @Json(name = "tagline")
    var tagline: String = "",
    @Json(name = "title")
    var title: String = "",
    @Json(name = "video")
    var video: Boolean = false,
    @Json(name = "vote_average")
    var voteAverage: Double = 0.0,
    @Json(name = "vote_count")
    var voteCount: Int = 0
)