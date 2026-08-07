package com.solo4.core.calendar

actual fun getPlatformCalendar(): CalendarWrapper {
    return IosCalendar()
}

class IosCalendar : CalendarWrapper {

    override val timeZoneOffset: Int = 0
    override val millisNow: Long = 0

    override fun timeZoneOffsetAt(millis: Long): Int = 0

    override fun yearOf(dateMillis: Long): Int = 1

    override fun monthOf(dateMillis: Long): Int = 1

    override fun dayOfMonthOf(dateMillis: Long): Int = 1

    override fun millisOf(year: Int, month: Int, day: Int): Long = 0

    override fun toDatePickerMillis(localDayStartMillis: Long): Long = localDayStartMillis

    override fun fromDatePickerMillis(datePickerUtcMillis: Long): Long = datePickerUtcMillis

    override fun getDisplayMonthName(month: Int): String = "Not implemented yet"
}
