package com.solo4.domain.eventmanager

import com.solo4.core.calendar.CalendarWrapper
import com.solo4.core.calendar.model.CalendarEvent
import com.solo4.core.kmputils.MultiplatformContext

expect fun getEventsNotificationManager(
    context: MultiplatformContext,
    calendar: CalendarWrapper
): EventsNotificationManager

interface EventsNotificationManager {
    fun scheduleEvent(
        event: CalendarEvent,
        scheduleBeforeMillis: Long
    )

    fun canScheduleEvent() : Boolean
}