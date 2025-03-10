package com.solo4.calendarreminder.calendar.data.repository.eventdetails

import com.solo4.calendarreminder.calendar.data.database.dao.EventsDao
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import com.solo4.core.calendar.model.CalendarEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class EventDetailsRepository(
    private val eventsDao: EventsDao,
    private val eventMapper: CalendarEventMapper,
) {

    suspend fun removeEvent(eventId: Int) {
        withContext(Dispatchers.IO) {
            eventsDao.removeEventById(eventId)
        }
    }

    suspend fun saveEvent(event: CalendarEvent) {
        withContext(Dispatchers.IO) {
            eventsDao.setEvent(eventMapper.mapToDayEventRelation(event))
        }
    }
}