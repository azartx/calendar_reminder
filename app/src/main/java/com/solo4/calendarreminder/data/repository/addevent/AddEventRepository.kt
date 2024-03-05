package com.solo4.calendarreminder.data.repository.addevent

import com.solo4.calendarreminder.data.database.dao.EventsDao
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import com.solo4.calendarreminder.data.model.CalendarEvent

class AddEventRepository(
    private val eventsDao: EventsDao,
    private val calendarEventMapper: CalendarEventMapper
) {

    suspend fun saveEvent(event: CalendarEvent) {
        eventsDao.setEvent(calendarEventMapper.mapToDayEventRelation(event))
    }
}