package com.solo4.calendarreminder.data.database

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

expect fun calendarEventsDatabaseBuilder(): RoomDatabase.Builder<CalendarEventsDatabase>

object CalendarEventsDatabaseHolder {
    val instance by lazy {
        calendarEventsDatabaseBuilder()
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration(false)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}
