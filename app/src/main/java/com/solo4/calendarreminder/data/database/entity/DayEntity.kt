package com.solo4.calendarreminder.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "days_table")
data class DayEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val dayMillis: Long
)