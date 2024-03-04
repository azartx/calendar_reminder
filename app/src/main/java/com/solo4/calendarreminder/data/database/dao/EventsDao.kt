package com.solo4.calendarreminder.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.solo4.calendarreminder.data.database.entity.DayEntity

@Dao
interface EventsDao {

    @Query("SELECT * FROM days_table WHERE id = :dayId")
    suspend fun getDayById(dayId: Long): DayEntity?

  //  @Insert()
}