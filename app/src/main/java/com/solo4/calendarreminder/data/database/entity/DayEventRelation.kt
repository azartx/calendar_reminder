package com.solo4.calendarreminder.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class DayEventRelation(
    @Embedded
    val day: DayEntity,

    @Relation(
        entity = EventEntity::class,
        parentColumn = "id",
        entityColumn = "day_id"
    )
    val events: List<EventEntity>
)