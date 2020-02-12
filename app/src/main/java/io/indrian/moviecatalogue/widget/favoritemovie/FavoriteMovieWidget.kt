package io.indrian.moviecatalogue.widget.favoritemovie

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.net.toUri
import io.indrian.moviecatalogue.R

class FavoriteMovieWidget : AppWidgetProvider() {

    companion object {

        private const val TOAST_ACTION = "io.indrian.moviecatalogue.widget.favoritemovie.TOAST_ACTION"
        const val EXTRA_MOVIE = "io.indrian.moviecatalogue.widget.favoritemovie.EXTRA_ITEM"

        private fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            val intent = Intent(context, MovieStackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val views = RemoteViews(context.packageName, R.layout.favorite_movie_widget)
            views.setRemoteAdapter(R.id.movie_stack_view, intent)
            views.setEmptyView(R.id.movie_stack_view, R.id.empty_view)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}