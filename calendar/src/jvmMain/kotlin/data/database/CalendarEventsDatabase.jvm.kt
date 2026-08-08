package com.solo4.calendarreminder.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

actual fun calendarEventsDatabaseBuilder(): RoomDatabase.Builder<CalendarEventsDatabase> {
    val dbFile = File(resolveAppDataDir(), "calendar_reminder.db")
    return Room.databaseBuilder<CalendarEventsDatabase>(
        name = dbFile.absolutePath,
    )
}

/**
 * Prefer %APPDATA%/CalendarReminder on Windows; fall back to ~/.calendar_reminder.
 * Avoids java.io.tmpdir so events survive reboots and temp cleanup.
 */
private fun resolveAppDataDir(): File {
    val appData = System.getenv("APPDATA")
    val dir = if (!appData.isNullOrBlank()) {
        File(appData, "CalendarReminder")
    } else {
        File(System.getProperty("user.home"), ".calendar_reminder")
    }
    if (!dir.exists()) {
        dir.mkdirs()
    }
    return dir
}
