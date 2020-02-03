package io.indrian.moviecatalogue.data.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailTVShowEntity(
    @Json(name = "backdrop_path")
    var backdropPath: String = "",
    @Json(name = "episode_run_time")
    var episodeRunTime: List<Int> = arrayListOf(),
    @Json(name = "first_air_date")
    var firstAirDate: String = "",
    @Json(name = "genres")
    var genreEntities: List<GenreEntity> = arrayListOf(),
    @Json(name = "homepage")
    var homepage: String = "",
    @Json(name = "id")
    var id: Int = 0,
    @Json(name = "in_production")
    var inProduction: Boolean = false,
    @Json(name = "languages")
    var languages: List<String> = arrayListOf(),
    @Json(name = "last_air_date")
    var lastAirDate: String = "",
    @Json(name = "name")
    var name: String = "",
    @Json(name = "next_episode_to_air")
    var nextEpisodeToAir: Any = Any(),
    @Json(name = "number_of_episodes")
    var numberOfEpisodes: Int = 0,
    @Json(name = "number_of_seasons")
    var numberOfSeasons: Int = 0,
    @Json(name = "origin_country")
    var originCountry: List<String> = arrayListOf(),
    @Json(name = "original_language")
    var originalLanguage: String = "",
    @Json(name = "original_name")
    var originalName: String = "",
    @Json(name = "overview")
    var overview: String = "",
    @Json(name = "popularity")
    var popularity: Double = 0.0,
    @Json(name = "poster_path")
    var posterPath: String = "",
    @Json(name = "production_companies")
    var productionCompanies: List<Any> = arrayListOf(),
    @Json(name = "status")
    var status: String = "",
    @Json(name = "type")
    var type: String = "",
    @Json(name = "vote_average")
    var voteAverage: Double = 0.0,
    @Json(name = "vote_count")
    var voteCount: Int = 0
)