package com.solo4.calendarreminder.calendar.data.repository.calendar

import com.solo4.calendarreminder.calendar.data.database.dao.EventsDao
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import com.solo4.core.calendar.model.CalendarEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class CalendarRepository(
    private val mapper: CalendarEventMapper,
    private val eventsDao: EventsDao
) {

    suspend fun getMonthEvents(yearMonthDay: Long): List<CalendarEvent> {
        return withContext(Dispatchers.IO) {
            mapper.mapToCalendarEvents(
                dayEventRelation = eventsDao.getDayById(yearMonthDay) ?: return@withContext emptyList()
            )
        }
    }

    suspend fun hasDayEvents(dayId: Long): Boolean {
        return withContext(Dispatchers.IO) {
            eventsDao.hasEvents(dayId)
        }
    }
}