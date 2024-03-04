package com.solo4.calendarreminder.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.solo4.calendarreminder.data.model.EventEntity

@Entity(tableName = "days_table")
data class DayEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @Relation(
        parentColumn = "id",
        entityColumn = "day_id"
    )
    @ColumnInfo(name = "day_events")
    val dayEvents: List<EventEntity>
)
