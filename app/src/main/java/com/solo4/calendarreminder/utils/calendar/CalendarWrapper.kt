package com.solo4.calendarreminder.utils.calendar

interface CalendarWrapper {

    val timeZoneOffset: Int

    fun yearOf(dateMillis: Long): Int

    /**
     * From 1 to 12
     * */
    fun monthOf(dateMillis: Long): Int

    /**
     * From 1 to Last month day
     * */
    fun dayOfMonthOf(dateMillis: Long): Int
}