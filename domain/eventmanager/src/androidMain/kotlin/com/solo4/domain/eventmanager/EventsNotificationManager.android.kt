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
        scheduleBeforeMillis: Long /*if (BuildConfig.DEBUG)
            Millis.SECONDS_15.millis else Millis.MINUTES_15.millis*/ // todo debug settings
    ) {
        if (!isFeatureEvent(event, scheduleBeforeMillis)) return
        if (!canScheduleEvent()) return

        val intent = Intent(_context, CalendarNotificationsBroadcastReceiver::class.java)
        intent.putExtra(Event::class.java.name, mapper.map(event))

        val pendingIntent = PendingIntent.getBroadcast(
            _context,
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