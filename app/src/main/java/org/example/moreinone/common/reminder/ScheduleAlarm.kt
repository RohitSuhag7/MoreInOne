package org.example.moreinone.common.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import org.example.moreinone.utils.Constants
import java.util.Calendar

fun scheduleAlarm(
    context: Context,
    id: Int,
    title: String,
    year: Int,
    month: Int,
    day: Int,
    hour: Int,
    minute: Int
) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val alarmIntent = Intent(context, MyBroadcastReceiver::class.java)
    alarmIntent.putExtra(Constants.ID_KEY, id)
    alarmIntent.putExtra(Constants.TITLE_KEY, title)

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        id,
        alarmIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()

        // Set the date
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month - 1)
        set(Calendar.DAY_OF_MONTH, day)

        // Set the Time
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
    }

    // Check if the alarm time is in the past, if so, add one day
    if (calendar.timeInMillis <= System.currentTimeMillis()) {
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }

    // Schedule the alarm to repeat daily
    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
    )
}