package com.solo4.calendarreminder.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.solo4.calendarreminder.App
import com.solo4.calendarreminder.data.database.dao.EventsDao
import com.solo4.calendarreminder.data.database.entity.DayEntity
import com.solo4.calendarreminder.data.database.entity.EventEntity

@Database(
    entities = [
        DayEntity::class,
        EventEntity::class
    ],
    version = 1
)
abstract class CalendarEventsDatabase : RoomDatabase() {

    abstract val eventsDao: EventsDao

    companion object {

        val instance: CalendarEventsDatabase by lazy {
            Room.databaseBuilder(App.app, CalendarEventsDatabase::class.java, "calendar_events.db")
                .build()
        }
    }
}