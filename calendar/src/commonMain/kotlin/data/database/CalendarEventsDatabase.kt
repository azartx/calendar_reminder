package com.solo4.calendarreminder.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.solo4.calendarreminder.calendar.data.database.dao.EventsDao
import com.solo4.calendarreminder.data.database.entity.DayEntity
import com.solo4.calendarreminder.data.database.entity.EventEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [
        DayEntity::class,
        EventEntity::class
    ],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class CalendarEventsDatabase : RoomDatabase() {

    companion object Creator

    object InstanceHolder {

        val instance by lazy {
            Creator.builder()
               // .addMigrations(MIGRATIONS)
                .fallbackToDestructiveMigration(false)
                .setDriver(BundledSQLiteDriver())
                .setQueryCoroutineContext(Dispatchers.IO)
                .build()
        }
    }

    abstract val eventsDao: EventsDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<CalendarEventsDatabase> {
    override fun initialize(): CalendarEventsDatabase
}

expect fun CalendarEventsDatabase.Creator.builder(): RoomDatabase.Builder<CalendarEventsDatabase>