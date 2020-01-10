package io.indrian.moviecatalogue.data.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailTVShowEntity(
    @Json(name = "backdrop_path")
    var backdropPath: String?,
    @Json(name = "episode_run_time")
    var episodeRunTime: List<Int?>?,
    @Json(name = "first_air_date")
    var firstAirDate: String?,
    @Json(name = "genres")
    var genreEntities: List<GenreEntity?>?,
    @Json(name = "homepage")
    var homepage: String?,
    @Json(name = "id")
    var id: Int?,
    @Json(name = "in_production")
    var inProduction: Boolean?,
    @Json(name = "languages")
    var languages: List<String?>?,
    @Json(name = "last_air_date")
    var lastAirDate: String?,
    @Json(name = "name")
    var name: String?,
    @Json(name = "next_episode_to_air")
    var nextEpisodeToAir: Any?,
    @Json(name = "number_of_episodes")
    var numberOfEpisodes: Int?,
    @Json(name = "number_of_seasons")
    var numberOfSeasons: Int?,
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
    @Json(name = "production_companies")
    var productionCompanies: List<Any?>?,
    @Json(name = "status")
    var status: String?,
    @Json(name = "type")
    var type: String?,
    @Json(name = "vote_average")
    var voteAverage: Double?,
    @Json(name = "vote_count")
    var voteCount: Int?
)