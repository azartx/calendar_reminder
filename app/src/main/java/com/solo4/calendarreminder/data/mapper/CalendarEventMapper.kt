package com.solo4.calendarreminder.data.mapper

import com.solo4.calendarreminder.data.database.entity.DayEntity
import com.solo4.calendarreminder.data.database.entity.DayEventRelation
import com.solo4.calendarreminder.data.database.entity.EventEntity
import com.solo4.core.calendar.model.CalendarEvent

class CalendarEventMapper {

    fun mapToCalendarEvents(dayEventRelation: DayEventRelation): List<CalendarEvent> {
        return dayEventRelation.events
            .map { event ->
                CalendarEvent(
                    eventId = event.id,
                    dayMillis = dayEventRelation.day.yearMonthDaySum,
                    title = event.title,
                    description = event.description,
                    eventTimeMillis = event.eventTimeMillis
                )
            }
    }

    fun mapToDayEventRelation(
        events: List<CalendarEvent>,
        ofDayMillis: Long = events.firstOrNull()?.dayMillis ?: -1L
    ): DayEventRelation {
        require(events.isNotEmpty())

        return DayEventRelation(
            day = DayEntity(ofDayMillis),
            events = events.map { event ->
                EventEntity(
                    id = event.eventId,
                    dayId = event.dayMillis,
                    title = event.title,
                    description = event.description,
                    eventTimeMillis = event.eventTimeMillis
                )
            }
        )
    }

    fun mapToDayEventRelation(event: CalendarEvent): EventEntity {
        return EventEntity(
            id = event.eventId,
            dayId = event.dayMillis,
            title = event.title,
            description = event.description,
            eventTimeMillis = event.eventTimeMillis
        )
    }
}