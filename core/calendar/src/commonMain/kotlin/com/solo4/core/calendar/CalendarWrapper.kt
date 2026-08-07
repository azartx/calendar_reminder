package com.solo4.core.calendar

expect fun getPlatformCalendar(): CalendarWrapper

interface CalendarWrapper {

    /**
     * Default timezone offset at [millisNow], in milliseconds.
     * Prefer [timeZoneOffsetAt] when the instant matters (DST).
     */
    val timeZoneOffset: Int

    val millisNow: Long

    /**
     * Timezone offset of the default zone at the given instant, in milliseconds.
     */
    fun timeZoneOffsetAt(millis: Long): Int

    fun yearOf(dateMillis: Long): Int

    /**
     * From 1 to 12
     */
    fun monthOf(dateMillis: Long): Int

    /**
     * From 1 to last day of month
     */
    fun dayOfMonthOf(dateMillis: Long): Int

    /**
     * Local-zone start of day (00:00:00.000) for the given calendar date.
     * [month] is 1-based (1..12).
     */
    fun millisOf(year: Int, month: Int, day: Int): Long

    /**
     * Converts local day-start millis to Material3 DatePicker UTC-midnight millis
     * for the same calendar Y-M-D.
     */
    fun toDatePickerMillis(localDayStartMillis: Long): Long

    /**
     * Converts Material3 DatePicker UTC-midnight millis to local day-start millis
     * for the same calendar Y-M-D.
     */
    fun fromDatePickerMillis(datePickerUtcMillis: Long): Long

    /**
     * String name of the provided month
     */
    fun getDisplayMonthName(month: Int): String
}
