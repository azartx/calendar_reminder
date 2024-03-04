package com.solo4.calendarreminder.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.solo4.calendarreminder.data.database.entity.EventEntity

@Dao
interface EventsDao {

    @Query("SELECT * FROM events_table")
    suspend fun getAllEvents(): List<EventEntity>

    @Query("SELECT * FROM events_table WHERE eventTimeMillis = :eventTime")
    suspend fun getEventByTimeStamp(eventTime: Long): EventEntity?
}