package org.example.moreinone.common.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.example.moreinone.R
import org.example.moreinone.utils.Constants

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val id = p1?.getIntExtra(Constants.ID_KEY, Constants.NOTIFICATION_ID)
        val title = p1?.getStringExtra(Constants.TITLE_KEY)

        NotificationHandler(p0!!).showNotification(
            id = id ?: Constants.NOTIFICATION_ID,
            title = p0.getString(R.string.todo),
            contentText = title ?: Constants.TITLE_KEY,
            icon = R.drawable.alarmclock
        )
    }
}