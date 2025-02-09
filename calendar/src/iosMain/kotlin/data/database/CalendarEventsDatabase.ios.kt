package com.solo4.calendarreminder.data.database

import androidx.room.Room
import androidx.room.RoomDatabase

actual fun CalendarEventsDatabase.Creator.builder(): RoomDatabase.Builder<CalendarEventsDatabase> {
    val dbFilePath = documentDirectory() + "/my_room.db"
    return Room.databaseBuilder<CalendarEventsDatabase>(
        name = dbFilePath,
    )
}

private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}