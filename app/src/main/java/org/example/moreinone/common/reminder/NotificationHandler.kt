package org.example.moreinone.common.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import org.example.moreinone.utils.Constants.NOTIFICATION_CHANNEL_ID
import org.example.moreinone.utils.Constants.NOTIFICATION_CHANNEL_NAME

class NotificationHandler(private val context: Context) {

    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
       notificationManager.createNotificationChannel(notificationChannel)
    }

    fun showNotification(id: Int, title: String, contentText: String, icon: Int) {
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(contentText)
            .setSmallIcon(icon)
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
            .setAutoCancel(true)
            .build()

        Log.d("Alarm", "showNotification: $id")

        notificationManager.notify(id, notification)
    }
}