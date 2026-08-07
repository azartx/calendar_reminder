package com.solo4.domain.eventmanager

import com.solo4.core.calendar.CalendarWrapper
import com.solo4.core.calendar.model.CalendarEvent
import com.solo4.core.kmputils.MultiplatformContext

expect fun getEventsNotificationManager(
    context: MultiplatformContext,
    calendar: CalendarWrapper
): EventsNotificationManager

data class ScheduledReminder(
    val event: CalendarEvent,
    val scheduleBeforeMillis: Long
)

interface EventsNotificationManager {
    fun scheduleEvent(
        event: CalendarEvent,
        scheduleBeforeMillis: Long
    )

    fun cancelEvent(eventId: Int)

    fun rescheduleEvent(
        event: CalendarEvent,
        scheduleBeforeMillis: Long
    )

    fun restoreScheduledEvents(reminders: List<ScheduledReminder>)

    fun canScheduleEvent(): Boolean
}
