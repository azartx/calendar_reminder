package com.solo4.calendarreminder.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

actual fun CalendarEventsDatabase.Creator.builder(): RoomDatabase.Builder<CalendarEventsDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "calendar_reminder.db")
    return Room.databaseBuilder<CalendarEventsDatabase>(
        name = dbFile.absolutePath,
    )
}