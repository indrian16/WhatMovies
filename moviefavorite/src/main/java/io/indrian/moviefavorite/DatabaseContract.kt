package io.indrian.moviefavorite

import android.net.Uri

object DatabaseContract {

    private const val AUTHORITY = "io.indrian.moviecatalogue"
    val CONTENT_URI: Uri = Uri.Builder()
        .scheme("content")
        .authority(AUTHORITY)
        .appendPath("favorite_movie")
        .build()
}