package com.solo4.calendarreminder.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.solo4.calendarreminder.calendar.App

actual fun CalendarEventsDatabase.Creator.builder(): RoomDatabase.Builder<CalendarEventsDatabase> {
    val appContext = App.app.applicationContext
    val dbFile = appContext.getDatabasePath("calendar_reminder.db")
    return Room.databaseBuilder<CalendarEventsDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}