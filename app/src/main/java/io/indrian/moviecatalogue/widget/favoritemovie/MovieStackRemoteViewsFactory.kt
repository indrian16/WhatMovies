package io.indrian.moviecatalogue.widget.favoritemovie

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import io.indrian.moviecatalogue.R
import io.indrian.moviecatalogue.di.GlideApp
import io.indrian.moviecatalogue.provider.MovieFavoriteContentProvider
import java.util.concurrent.ExecutionException


internal class MovieStackRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private val posterBitmap = ArrayList<Bitmap>()

    private var posterImages: List<String> = arrayListOf()

    private lateinit var cursor: Cursor

    override fun onCreate() {

        cursor = context.applicationContext.contentResolver?.query(
            MovieFavoriteContentProvider.URI_MOVIE_FAVORITE,
            null,
            null,
            null,
            null
        )!!
        val list = generateSequence { if (cursor.moveToNext()) cursor else null }
            .map {

                it.getString(it.getColumnIndex("poster"))
            }
            .toList()

        posterImages = list
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {

        for (i in posterImages) {

            try {

                val bitmap = GlideApp.with(context.applicationContext)
                    .asBitmap()
                    .load(i)
                    .submit(100, 150)
                    .get()
                posterBitmap.add(bitmap)

            } catch (e: InterruptedException) {

                e.printStackTrace()
            } catch (e: ExecutionException) {

                e.printStackTrace()
            }
        }
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {

        val rv = RemoteViews(context.packageName, R.layout.widget_item)

        rv.setImageViewBitmap(R.id.img_poster, posterBitmap[position])
        return rv
    }

    override fun getCount(): Int = posterBitmap.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {

        cursor.close()
    }
}