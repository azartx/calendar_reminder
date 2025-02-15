package com.solo4.domain.eventmanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.solo4.core.calendar.model.CalendarEvent
import com.solo4.domain.eventmanager.mapper.CalendarEventMapper
import com.solo4.domain.eventmanager.model.Event

private const val CALENDAR_EVENTS_CHANNEL_ID = "CM_channel"

class CalendarNotificationsBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val mapper by lazy { CalendarEventMapper() }
        val event = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(CalendarEvent::class.simpleName, Event::class.java)
        } else {
            intent?.getParcelableExtra(CalendarEvent::class.simpleName)
        }
            ?.let(mapper::map)
            ?: return

        context?.apply {
            val nm = getSystemService(NotificationManager::class.java)

            val notification = Notification.Builder(this, CALENDAR_EVENTS_CHANNEL_ID)
                .setContentTitle(event.title)
                .setContentText(event.description)
                .setSmallIcon(androidx.core.R.drawable.notification_bg_low) // todo
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