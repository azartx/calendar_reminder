package com.solo4.domain.eventmanager

import com.solo4.core.calendar.CalendarWrapper
import com.solo4.core.calendar.model.CalendarEvent
import com.solo4.core.kmputils.MultiplatformContext

// TODO implement notifications on JVM
actual fun getEventsNotificationManager(
    context: MultiplatformContext,
    calendar: CalendarWrapper
): EventsNotificationManager {
    return object : EventsNotificationManager {
        override fun scheduleEvent(event: CalendarEvent, scheduleBeforeMillis: Long) {}

        override fun cancelEvent(eventId: Int) {}

        override fun rescheduleEvent(event: CalendarEvent, scheduleBeforeMillis: Long) {}

        override fun restoreScheduledEvents(reminders: List<ScheduledReminder>) {}

        override fun canScheduleEvent(): Boolean = false
    }
}
