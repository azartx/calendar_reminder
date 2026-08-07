package com.solo4.core.calendar

import java.time.Month
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import java.util.TimeZone

actual fun getPlatformCalendar(): CalendarWrapper {
    return JvmCalendar()
}

class JvmCalendar : CalendarWrapper {

    private val timeZone: TimeZone = TimeZone.getDefault()
    private val locale: Locale = Locale.getDefault(Locale.Category.FORMAT)
    private val utcTimeZone: TimeZone = TimeZone.getTimeZone("UTC")

    private val calendar: GregorianCalendar = GregorianCalendar(timeZone, locale)
    private val utcCalendar: GregorianCalendar = GregorianCalendar(utcTimeZone, locale)

    override val timeZoneOffset: Int
        get() = timeZoneOffsetAt(millisNow)

    override val millisNow: Long
        get() = System.currentTimeMillis()

    override fun timeZoneOffsetAt(millis: Long): Int {
        return timeZone.getOffset(millis)
    }

    override fun yearOf(dateMillis: Long): Int {
        return synchronized(this) {
            setTimeMillis(dateMillis)
            calendar.get(Calendar.YEAR)
        }
    }

    override fun monthOf(dateMillis: Long): Int {
        return synchronized(this) {
            setTimeMillis(dateMillis)
            calendar.get(Calendar.MONTH) + 1 // month number starts from 0
        }
    }

    override fun dayOfMonthOf(dateMillis: Long): Int {
        return synchronized(this) {
            setTimeMillis(dateMillis)
            calendar.get(Calendar.DAY_OF_MONTH)
        }
    }

    override fun millisOf(year: Int, month: Int, day: Int): Long {
        return synchronized(this) {
            calendar.clear()
            calendar.set(year, month - 1, day, 0, 0, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.timeInMillis
        }
    }

    override fun toDatePickerMillis(localDayStartMillis: Long): Long {
        return synchronized(this) {
            setTimeMillis(localDayStartMillis)
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            utcCalendar.clear()
            utcCalendar.set(year, month, day, 0, 0, 0)
            utcCalendar.set(Calendar.MILLISECOND, 0)
            utcCalendar.timeInMillis
        }
    }

    override fun fromDatePickerMillis(datePickerUtcMillis: Long): Long {
        return synchronized(this) {
            utcCalendar.timeInMillis = datePickerUtcMillis
            val year = utcCalendar.get(Calendar.YEAR)
            val month = utcCalendar.get(Calendar.MONTH) + 1
            val day = utcCalendar.get(Calendar.DAY_OF_MONTH)
            millisOf(year, month, day)
        }
    }

    private fun setTimeMillis(dateMillis: Long) {
        calendar.time = Date(dateMillis)
    }

    override fun getDisplayMonthName(month: Int): String {
        return Month.of(month)
            .getDisplayName(TextStyle.FULL_STANDALONE, locale)
    }
}
