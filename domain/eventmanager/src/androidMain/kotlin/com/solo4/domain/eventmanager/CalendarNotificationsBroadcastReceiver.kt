package com.solo4.domain.eventmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.solo4.domain.eventmanager.mapper.CalendarEventMapper
import com.solo4.domain.eventmanager.model.Event

private const val CALENDAR_EVENTS_CHANNEL_ID = "CM_channel"
private const val TAG = "CalendarNotificationsBroadcastReceiver"

class CalendarNotificationsBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val appContext = context?.applicationContext ?: return
        val mapper = CalendarEventMapper()
        val event = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(Event::class.simpleName, Event::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent?.getParcelableExtra(Event::class.simpleName)
        }
            ?.let(mapper::map)

        if (event == null) {
            Log.e(TAG, "Calendar event for notification is null")
            return
        }

        val notificationManager = NotificationManagerCompat.from(appContext)
        if (!notificationManager.areNotificationsEnabled()) {
            Log.w(TAG, "Notifications are disabled; skip notify for eventId=${event.eventId}")
            return
        }

        createNotificationChannelIfNeed(appContext)

        val contentIntent = appContext.packageManager
            .getLaunchIntentForPackage(appContext.packageName)
            ?.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra(NotificationExtras.EXTRA_EVENT_ID, event.eventId)
            }
            ?.let { launchIntent ->
                PendingIntent.getActivity(
                    appContext,
                    event.eventId,
                    launchIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            }

        val smallIcon = appContext.resources.getIdentifier(
            "ic_notification",
            "drawable",
            appContext.packageName
        ).takeIf { it != 0 } ?: android.R.drawable.ic_menu_my_calendar

        val notification = NotificationCompat.Builder(appContext, CALENDAR_EVENTS_CHANNEL_ID)
            .setContentTitle(event.title)
            .setContentText(event.description)
            .setSmallIcon(smallIcon)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_EVENT)
            .build()

        try {
            notificationManager.notify(event.eventId, notification)
        } catch (e: SecurityException) {
            Log.e(TAG, "Missing notification permission for eventId=${event.eventId}", e)
        }
    }

    private fun createNotificationChannelIfNeed(context: Context) {
        val nm = context.getSystemService(NotificationManager::class.java) ?: return
        if (nm.getNotificationChannel(CALENDAR_EVENTS_CHANNEL_ID) != null) return

        val channelName = context.resources.getIdentifier(
            "calendar_events_channel_name",
            "string",
            context.packageName
        ).takeIf { it != 0 }?.let(context::getString) ?: "Calendar reminders"

        val channelDescription = context.resources.getIdentifier(
            "calendar_events_channel_description",
            "string",
            context.packageName
        ).takeIf { it != 0 }?.let(context::getString)
            ?: "Notifications for upcoming calendar events"

        val channel = NotificationChannel(
            CALENDAR_EVENTS_CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = channelDescription
        }
        nm.createNotificationChannel(channel)
    }
}
