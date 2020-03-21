package io.indrian.whatmovies.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.github.ajalt.timberkt.d
import io.indrian.whatmovies.R
import java.util.*

class DailyMovieReceiver : BroadcastReceiver() {

    companion object {

        const val NOTIFICATION_ID = 1
        private var CHANNEL_ID = "daily_movie"
        private var CHANNEL_NAME: CharSequence = "movie_channel"
    }

    private var alarmManager: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

    override fun onReceive(context: Context, intent: Intent) {

        val resources = context.resources

        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.icons8_movie)
            .setContentTitle(resources.getString(R.string.app_name))
            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
            .setContentText(resources.getString(R.string.daily_notification_content))
            .setSound(sound)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            builder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    fun startDaily(context: Context) {

        d { "startDaily" }
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, DailyMovieReceiver::class.java).let { intent ->

            PendingIntent.getBroadcast(context, 0, intent, 0)
        }
        val calendar: Calendar = Calendar.getInstance().apply {

            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 7)
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

    fun stopDaily() {

        alarmManager?.cancel(alarmIntent)
    }
}
