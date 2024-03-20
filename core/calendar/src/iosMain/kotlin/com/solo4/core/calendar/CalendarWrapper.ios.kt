package com.solo4.core.calendar

actual fun getPlatformCalendar(): CalendarWrapper {
    return IosCalendar()
}

class IosCalendar : CalendarWrapper {

    override val timeZoneOffset: Int = 1
    override val millisNow: Long = 1

    override fun yearOf(dateMillis: Long): Int {
        return 1
    }

    override fun monthOf(dateMillis: Long): Int {
        return 1
    }

    override fun dayOfMonthOf(dateMillis: Long): Int {
        return 1
    }

    override fun millisOf(year: Int, month: Int, day: Int): Long {
        return 1
    }
}