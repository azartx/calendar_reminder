package com.solo4.calendarreminder.calendar.domain

import com.solo4.calendarreminder.data.database.dao.EventsDao
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import com.solo4.core.calendar.CalendarWrapper
import com.solo4.domain.eventmanager.EventsNotificationManager
import com.solo4.domain.eventmanager.ScheduledReminder

class RestoreRemindersUseCase(
    private val eventsDao: EventsDao,
    private val mapper: CalendarEventMapper,
    private val eventsNotificationManager: EventsNotificationManager,
    private val calendar: CalendarWrapper,
) {

    suspend operator fun invoke() {
        val now = calendar.millisNow
        val reminders = eventsDao.getAllEvents()
            .map(mapper::mapToCalendarEvent)
            .filter { event ->
                event.scheduleBeforeMillis > 0L &&
                    now < event.eventTimeMillis - event.scheduleBeforeMillis
            }
            .map { event ->
                ScheduledReminder(
                    event = event,
                    scheduleBeforeMillis = event.scheduleBeforeMillis
                )
            }

        eventsNotificationManager.restoreScheduledEvents(reminders)
    }
}
