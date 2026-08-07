package com.solo4.domain.eventmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
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
) : EventsNotificationManager {

    private val _context: Context
        get() {
            return context.getContext() as Context
        }

    private val alarmManager by lazy { _context.getSystemService(AlarmManager::class.java) }

    override fun scheduleEvent(
        event: CalendarEvent,
        scheduleBeforeMillis: Long
    ) {
        if (event.eventId <= 0) {
            Log.e(TAG, "Cannot schedule event without a valid eventId")
            return
        }
        // 0 means "no reminder" (Millis.NONE)
        if (scheduleBeforeMillis <= 0L) {
            Log.d(TAG, "Skip scheduling: reminder disabled for eventId=${event.eventId}")
            return
        }
        if (!isFutureEvent(event, scheduleBeforeMillis)) {
            Log.w(TAG, "Past event can't be scheduled for notifying")
            return
        }
        if (!canScheduleEvent()) {
            Log.e(TAG, "Alarm manager can't schedule the event")
            return
        }

        val pendingIntent = createPendingIntent(event)

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

    override fun cancelEvent(eventId: Int) {
        if (eventId <= 0) return
        val pendingIntent = createPendingIntent(eventId)
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }

    override fun rescheduleEvent(
        event: CalendarEvent,
        scheduleBeforeMillis: Long
    ) {
        cancelEvent(event.eventId)
        if (scheduleBeforeMillis <= 0L) return
        scheduleEvent(event, scheduleBeforeMillis)
    }

    override fun restoreScheduledEvents(reminders: List<ScheduledReminder>) {
        reminders.forEach { reminder ->
            scheduleEvent(reminder.event, reminder.scheduleBeforeMillis)
        }
    }

    private fun isFutureEvent(event: CalendarEvent, scheduleBeforeMillis: Long): Boolean {
        val scheduleTimeMillis = event.eventTimeMillis - scheduleBeforeMillis
        return calendar.millisNow < scheduleTimeMillis
    }

    override fun canScheduleEvent(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }

    private fun createPendingIntent(event: CalendarEvent): PendingIntent {
        val intent = Intent(_context, CalendarNotificationsBroadcastReceiver::class.java).apply {
            putExtra(Event::class.simpleName, mapper.map(event))
        }
        return PendingIntent.getBroadcast(
            _context,
            event.eventId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createPendingIntent(eventId: Int): PendingIntent {
        val intent = Intent(_context, CalendarNotificationsBroadcastReceiver::class.java)
        return PendingIntent.getBroadcast(
            _context,
            eventId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
