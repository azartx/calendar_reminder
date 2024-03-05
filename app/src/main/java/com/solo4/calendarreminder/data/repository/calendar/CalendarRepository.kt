package com.solo4.calendarreminder.data.repository.calendar

import com.solo4.calendarreminder.data.database.dao.EventsDao
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import com.solo4.calendarreminder.data.model.CalendarEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CalendarRepository(
    private val eventsDao: EventsDao,
    private val calendarEventMapper: CalendarEventMapper
) {

    suspend fun getMonthEvents(yearMonthDay: Long): List<CalendarEvent> {
        return withContext(Dispatchers.IO) {
            calendarEventMapper.mapToCalendarEvents(
                eventsDao.getDayById(yearMonthDay) ?: return@withContext emptyList()
            )
        }
    }
}