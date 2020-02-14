package io.indrian.moviecatalogue.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import io.indrian.moviecatalogue.R
import io.indrian.moviecatalogue.data.model.Movie
import io.indrian.moviecatalogue.data.repositories.Repository
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*

class LatestMovieTodayReceiver : BroadcastReceiver(), KoinComponent {

    private lateinit var disposable: Disposable
    private val repository: Repository by inject()

    private var idNotification = 1
    private var alarmManager: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

    companion object {

        private var CHANNEL_ID = "latest_movie"
        private const val CHANNEL_NAME = "latest_movie_channel"
        private const val GROUP_KEY_EMAILS = "group_latest_channel"
        private const val MAX_NOTIFICATION = 3
    }

    override fun onReceive(context: Context, intent: Intent) {

        d { "onReceive" }
        val date = Calendar.getInstance().time
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = sdf.format(date)
        disposable = repository.getLatestMovieToday(currentDate, currentDate)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { movies ->

                    d { "Movies: ${movies.size}" }
                    showNotification(context, movies)
                },
                { error ->

                    e { "Error: ${error.message}" }
                }
            )
    }

    private fun showNotification(context: Context, movies: List<Movie>) {

        val resources = context.resources

        val notificationManager = NotificationManagerCompat.from(context)
        var builder: NotificationCompat.Builder?
        for (movie in movies) {

            if (idNotification < MAX_NOTIFICATION) {

                builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.icons8_movie)
                    .setContentTitle(resources.getString(R.string.new_movie) + " ${movie.title}")
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setContentText(movie.overview)
                    .setGroup(GROUP_KEY_EMAILS)
                    .setAutoCancel(true)

            } else {

                val inboxStyle = NotificationCompat.InboxStyle()
                    .addLine(resources.getString(R.string.more_new_movie))
                    .setBigContentTitle("$idNotification ${resources.getString(R.string.new_movie)}")
                    .setSummaryText(resources.getString(R.string.app_name))

                builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.icons8_movie)
                    .setContentTitle(resources.getString(R.string.new_movie) + " ${movie.title}")
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setContentText(movie.overview)
                    .setGroup(GROUP_KEY_EMAILS)
                    .setGroupSummary(true)
                    .setStyle(inboxStyle)
                    .setAutoCancel(true)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )

                builder?.setChannelId(CHANNEL_ID)
                notificationManager.createNotificationChannel(channel)
            }

            val notification = builder.build()
            notificationManager.notify(idNotification, notification)
            idNotification++
        }
    }

    fun onSubscribe(context: Context) {

        d { "onSubscribe" }
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, LatestMovieTodayReceiver::class.java).let { intent ->

            PendingIntent.getBroadcast(context, 0, intent, 0)
        }
        val calendar: Calendar = Calendar.getInstance().apply {

            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        alarmManager?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmIntent
        )
    }

    fun unSubscribe() {

        alarmManager?.cancel(alarmIntent)
    }
}
