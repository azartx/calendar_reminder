package com.solo4.calendarreminder.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.solo4.calendarreminder.data.database.entity.DayEntity
import com.solo4.calendarreminder.data.database.entity.DayEventRelation
import com.solo4.calendarreminder.data.database.entity.EventEntity

@Dao
interface EventsDao {

    @Query("SELECT * FROM days_table WHERE id = :dayId")
    suspend fun getDayById(dayId: Long): DayEventRelation?

    @Transaction
    @Query("SELECT * FROM days_table WHERE id = :id")
    fun getDayEventsById(id: Long): List<DayEventRelation>

    @Query("SELECT EXISTS(SELECT * FROM days_table WHERE id = :dayId)")
    suspend fun isDayExists(dayId: Long): Boolean

    @Insert(entity = DayEntity::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun setDay(dayEntity: DayEntity)

    @Insert(entity = EventEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun setEvent(eventEntity: EventEntity)

    @Insert(entity = EventEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun setEvents(eventEntity: List<EventEntity>)

    @Transaction
    suspend fun setDayEvent(dayEvent: DayEventRelation) {
        if (!isDayExists(dayEvent.day.dayMillis)) {
            setDay(dayEvent.day)
        }

        setEvents(dayEvent.events)
    }
}