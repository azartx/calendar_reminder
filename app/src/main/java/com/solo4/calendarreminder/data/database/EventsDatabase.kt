package com.solo4.calendarreminder.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.solo4.calendarreminder.data.database.dao.EventsDao

@Database(
    entities = [],
    version = 1
)
abstract class EventsDatabase : RoomDatabase() {

    abstract val eventsDao: EventsDao
}