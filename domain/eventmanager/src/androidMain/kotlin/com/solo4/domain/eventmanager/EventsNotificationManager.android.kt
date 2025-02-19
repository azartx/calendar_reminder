package com.solo4.domain.eventmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.solo4.core.calendar.CalendarWrapper
import com.solo4.core.calendar.model.CalendarEvent
import com.solo4.core.kmputils.MultiplatformContext
import com.solo4.domain.eventmanager.mapper.CalendarEventMapper
import com.solo4.domain.eventmanager.model.Event

private const val TAG = "AndroidEventNotificationManager"

actual fun getEventsNotificationManager(
    context: MultiplatformContext,
    calendar: CalendarWrapper
): EventsNotificationManager {
    return AndroidEventsNotificationManager(
        context,
        calendar,
        CalendarEventMapper()
    )
}

internal class AndroidEventsNotificationManager(
    private val context: MultiplatformContext,
    private val calendar: CalendarWrapper,
    private val mapper: CalendarEventMapper
): EventsNotificationManager {

    private val _context: Context
        get() { return context.getContext() as Context }

    private val alarmManager by lazy { _context.getSystemService(AlarmManager::class.java) }

    override fun scheduleEvent(
        event: CalendarEvent,
        scheduleBeforeMillis: Long
    ) {
        if (!isFeatureEvent(event, scheduleBeforeMillis)) {
            Log.w(TAG, "Old event can't be posted for notifying")
            return
        }
        if (!canScheduleEvent()) {
            Log.e(TAG, "Alarm manager can't schedule the event")
            Toast.makeText(_context, "canScheduleEvent == false", Toast.LENGTH_LONG).show()
            return
        }

        val intent = Intent(_context, CalendarNotificationsBroadcastReceiver::class.java)
        intent.putExtra(Event::class.simpleName, mapper.map(event))

        val pendingIntent = PendingIntent.getBroadcast(
            _context,
            (0..1000).random(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                event.eventTimeMillis - scheduleBeforeMillis,
                pendingIntent
            )
        } catch (e: SecurityException) {
            Log.e(TAG, "Alarm manager is not allowed.", e)
        }
    }

    private fun isFeatureEvent(event: CalendarEvent, scheduleBeforeMillis: Long): Boolean {
        val scheduleTimeMillis = event.eventTimeMillis - scheduleBeforeMillis
        return calendar.millisNow < scheduleTimeMillis
    }

    override fun canScheduleEvent(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else true
    }
}