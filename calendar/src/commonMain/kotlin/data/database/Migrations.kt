package com.solo4.calendarreminder.data.database

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL(
            "ALTER TABLE events_table ADD COLUMN schedule_before_millis INTEGER NOT NULL DEFAULT 0"
        )
    }
}
