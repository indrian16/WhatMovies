package io.indrian.moviecatalogue.widget.favoritemovie

import android.content.Intent
import android.widget.RemoteViewsService

class MovieStackWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {

        return MovieStackRemoteViewsFactory(this.applicationContext)
    }
}