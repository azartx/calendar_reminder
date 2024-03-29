package com.solo4.calendarreminder.shared.data.database

import app.cash.sqldelight.db.SqlDriver
import com.solo4.calendarreminder.CalendarDatabase
import com.solo4.calendarreminder.Days
import com.solo4.calendarreminder.Events

expect class DriverFactory() {

    fun createDriver(dbName: String = "calendar_database.db"): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): CalendarDatabase {
    val driver = driverFactory.createDriver()
    return CalendarDatabase(driver)
}

object CalendarDB {

    private val calendarDatabase by lazy { createDatabase(DriverFactory()) }

    fun saveDayEvent(event: Events) {
        calendarDatabase.transaction {
            val isDayExist = calendarDatabase.daysTableQueries
                .isDayExists(event.day_id)
                .executeAsOne()

            if (!isDayExist) {
                calendarDatabase.daysTableQueries.setDay(Days(event.day_id))
            }

            calendarDatabase.eventsTableQueries.setEvent(
                Events(
                    event.id,
                    event.day_id,
                    event.title,
                    event.description,
                    event.event_time_millis
                )
            )
        }
    }
}