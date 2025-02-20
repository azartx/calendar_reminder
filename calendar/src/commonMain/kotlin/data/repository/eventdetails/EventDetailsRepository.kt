package com.solo4.calendarreminder.calendar.data.repository.eventdetails

import com.solo4.calendarreminder.calendar.data.database.dao.EventsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class EventDetailsRepository(private val eventsDao: EventsDao) {

    suspend fun removeEvent(eventId: Int) {
        withContext(Dispatchers.IO) {
            eventsDao.removeEventById(eventId)
        }
    }
}