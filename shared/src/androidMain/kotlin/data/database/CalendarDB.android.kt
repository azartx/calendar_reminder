package com.solo4.calendarreminder.shared.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.solo4.calendarreminder.CalendarDatabase
import com.solo4.calendarreminder.shared.App

actual class DriverFactory {
    actual fun createDriver(dbName: String): SqlDriver {
        return AndroidSqliteDriver(CalendarDatabase.Schema, App.app, dbName)
    }
}