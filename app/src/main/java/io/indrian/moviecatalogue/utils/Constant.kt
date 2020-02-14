package io.indrian.moviecatalogue.utils

import io.indrian.moviecatalogue.BuildConfig

object Constant {

    const val DEFAULT_LANGUAGE = "en"
    const val ID = "id"

    const val ID_ID = "id-ID"
    const val EN_US = "en-US"

    const val BASE_URL = BuildConfig.BASE_URL
    const val API_KEY = BuildConfig.API_KEY

    const val DB_VER = 1
    const val DB_NAME = "movie.db"

    const val TABLE_MOVIE = "movie"
    const val TABLE_TV_SHOW = "tv_show"
    const val TABLE_MOVIE_FAVORITE = "favorite_movie"
    const val TABLE_TV_SHOW_FAVORITE = "favorite_tv_show"

    const val SETTING_KEY_FILE = "movie_setting"
    const val DAILY_OPEN_APP = "daily_open_app"
    const val DAILY_LETEST_MOVIE = "daily_latest_move"
}