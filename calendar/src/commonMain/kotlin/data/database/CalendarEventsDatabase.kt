package com.solo4.calendarreminder.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
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
@ConstructedBy(AppDatabaseConstructor::class)
abstract class CalendarEventsDatabase : RoomDatabase() {
    abstract fun eventsDao(): EventsDao
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<CalendarEventsDatabase> {
    override fun initialize(): CalendarEventsDatabase
}