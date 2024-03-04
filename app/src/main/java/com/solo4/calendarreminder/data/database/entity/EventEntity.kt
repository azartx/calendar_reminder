package com.solo4.calendarreminder.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events_table")
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val eventTimeMillis: Long
)
