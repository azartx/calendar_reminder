package com.solo4.calendarreminder.calendar.data.repository.addevent

import com.solo4.calendarreminder.data.database.dao.EventsDao
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import com.solo4.core.calendar.model.CalendarEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class AddEventRepository(
    private val eventsDao: EventsDao,
    private val calendarEventMapper: CalendarEventMapper
) {

    suspend fun saveEvent(event: CalendarEvent) {
        withContext(Dispatchers.IO) {
            eventsDao.setDayEvent(calendarEventMapper.mapToDayEventRelation(event))
        }
    }
}