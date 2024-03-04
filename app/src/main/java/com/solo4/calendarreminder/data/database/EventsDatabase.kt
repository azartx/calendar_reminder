package com.solo4.calendarreminder.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.solo4.calendarreminder.data.database.dao.EventsDao
import com.solo4.calendarreminder.data.database.entity.DayEntity
import com.solo4.calendarreminder.data.model.EventEntity

@Database(
    entities = [
        DayEntity::class,
        EventEntity::class
    ],
    version = 1
)
abstract class EventsDatabase : RoomDatabase() {

    abstract val eventsDao: EventsDao
}