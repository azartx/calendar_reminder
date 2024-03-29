package com.solo4.core.calendar

expect fun getPlatformCalendar(): CalendarWrapper

interface CalendarWrapper {

    val timeZoneOffset: Int

    val millisNow: Long

    fun yearOf(dateMillis: Long): Int

    /**
     * From 1 to 12
     * */
    fun monthOf(dateMillis: Long): Int

    /**
     * From 1 to Last month day
     * */
    fun dayOfMonthOf(dateMillis: Long): Int

    /**
     * Get milliseconds of inputted date
     * */
    fun millisOf(year: Int, month: Int, day: Int): Long

    /**
     * String name of the provided month
     * */
    fun getDisplayMonthName(month: Int): String
}