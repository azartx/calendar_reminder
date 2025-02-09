package com.solo4.calendarreminder.calendar.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.solo4.calendarreminder.CalendarDatabase

actual class DriverFactory {
    actual fun createDriver(dbName: String): SqlDriver {
        return NativeSqliteDriver(CalendarDatabase.Schema, dbName)
    }
}