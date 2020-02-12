package io.indrian.moviefavorite

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    companion object {

        private val TAG = MainActivity::class.java.simpleName
    }

    private val mAdapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportLoaderManager.initLoader(1, null, this)

        rv_movie.adapter = mAdapter
    }

    override fun onResume() {
        super.onResume()
        supportLoaderManager.restartLoader(1, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {

        return CursorLoader(this, DatabaseContract.CONTENT_URI, null, null, null, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {

        val movies = generateSequence { if (data?.moveToNext()!!) data else null }
            .map {

                val date = it.getLong(it.getColumnIndex("release_date"))
                val name = it.getString(it.getColumnIndex("title"))
                val poster = it.getString(it.getColumnIndex("poster"))
                Movie(
                    name,
                    date,
                    poster
                )
            }
            .toList()

        for (movie in  movies) {

            Log.d(TAG, "Movie: $movie")
        }

        mAdapter.updateMovie(movies = movies)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

    }
}
