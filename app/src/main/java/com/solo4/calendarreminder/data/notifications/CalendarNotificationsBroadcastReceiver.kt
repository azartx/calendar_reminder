package com.solo4.calendarreminder.data.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.solo4.calendarreminder.R
import com.solo4.calendarreminder.utils.serializableExtra
import com.solo4.core.calendar.model.CalendarEvent

private const val CALENDAR_EVENTS_CHANNEL_ID = "CM_channel"

class CalendarNotificationsBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val event = intent.serializableExtra<CalendarEvent>(CalendarEvent::class.java.name) ?: return
        context?.apply {
            val nm = getSystemService(NotificationManager::class.java)

            val notification = Notification.Builder(this, CALENDAR_EVENTS_CHANNEL_ID)
                .setContentTitle(event.title)
                .setContentText(event.description)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setChannelId(CALENDAR_EVENTS_CHANNEL_ID)
                .build()

            createNotificationChannelIfNeed(nm)
            nm.notify(event.eventId, notification)
        }
    }

    private fun createNotificationChannelIfNeed(nm: NotificationManager) {
        if (nm.notificationChannels?.firstOrNull { it.id == CALENDAR_EVENTS_CHANNEL_ID } == null) {
            val ch = NotificationChannel(
                CALENDAR_EVENTS_CHANNEL_ID,
                CALENDAR_EVENTS_CHANNEL_ID,
                NotificationManager.IMPORTANCE_HIGH
            )
            nm.createNotificationChannel(ch)
        }
    }
}