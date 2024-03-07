package com.solo4.calendarreminder.data.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import com.solo4.calendarreminder.App
import com.solo4.calendarreminder.BuildConfig
import com.solo4.calendarreminder.data.model.CalendarEvent
import com.solo4.calendarreminder.data.utils.Millis

class EventsNotificationManager(private val context: App) {

    private val alarmManager by lazy { context.getSystemService(AlarmManager::class.java) }

    fun scheduleCalendarEvent(
        event: CalendarEvent,
        scheduleBeforeMillis: Long = if (BuildConfig.DEBUG)
            Millis.SECONDS_15.millis else Millis.MINUTES_15.millis
    ) {
        val intent = Intent(context, CalendarNotificationsBroadcastReceiver::class.java)
        intent.putExtra(CalendarEvent::class.java.name, event)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                event.eventTimeMillis - scheduleBeforeMillis,
                pendingIntent
            )
        } catch (e: SecurityException) {
            Log.e(this::class.java.name, "Alarm manager is not allowed.", e)
        }
    }
}