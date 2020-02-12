package io.indrian.moviecatalogue.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import io.indrian.moviecatalogue.data.db.AppDatabase
import io.indrian.moviecatalogue.utils.Constant

class MovieFavoriteContentProvider : ContentProvider() {

    companion object {

        const val AUTHORITY = "io.indrian.moviecatalogue.provider"
        val URI_MOVIE_FAVORITE = Uri.parse("content://$AUTHORITY/${Constant.TABLE_MOVIE_FAVORITE}")

        private const val FAVORITE_MOVIE = 1

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {

            sUriMatcher.addURI(AUTHORITY, Constant.TABLE_MOVIE_FAVORITE, FAVORITE_MOVIE)
        }
    }

    override fun onCreate(): Boolean {

        return true
    }

    override fun getType(uri: Uri): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {

        val code = sUriMatcher.match(uri)

        val db = context?.applicationContext?.let {
            Room.databaseBuilder(
                it,
                AppDatabase::class.java, Constant.DB_NAME
            ).allowMainThreadQueries().build()
        }

        val favoriteDao = db?.favoriteDao()

        if (code == FAVORITE_MOVIE) {

            return favoriteDao?.getAllFavoriteMovieCursor()

        } else {

            throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
