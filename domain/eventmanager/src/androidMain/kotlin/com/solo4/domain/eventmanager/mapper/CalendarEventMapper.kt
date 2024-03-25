package com.solo4.domain.eventmanager.mapper

import com.solo4.core.calendar.model.CalendarEvent
import com.solo4.domain.eventmanager.model.Event

internal class CalendarEventMapper {

    fun map(calendarEvent: CalendarEvent): Event {
        return Event(
            eventId = calendarEvent.eventId,
            dayMillis = calendarEvent.dayMillis,
            title = calendarEvent.title,
            description = calendarEvent.description,
            eventTimeMillis = calendarEvent.eventTimeMillis
        )
    }

    fun map(event: Event): CalendarEvent {
        return CalendarEvent(
            eventId = event.eventId,
            dayMillis = event.dayMillis,
            title = event.title,
            description = event.description,
            eventTimeMillis = event.eventTimeMillis
        )
    }
}