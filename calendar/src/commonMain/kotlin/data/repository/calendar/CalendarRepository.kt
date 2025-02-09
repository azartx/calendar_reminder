package com.solo4.calendarreminder.calendar.data.repository.calendar

import com.solo4.core.calendar.model.CalendarEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class CalendarRepository {

    suspend fun getMonthEvents(yearMonthDay: Long): List<CalendarEvent> {
        return withContext(Dispatchers.IO) {
            listOf()
            /*calendarEventMapper.mapToCalendarEvents(
                eventsDao.getDayById(yearMonthDay) ?: return@withContext emptyList()
            )*/
        }
    }

    suspend fun hasDayEvents(dayId: Long): Boolean {
        return withContext(Dispatchers.IO) {
           // eventsDao.hasEvents(dayId)
            false
        }
    }
}