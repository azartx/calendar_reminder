package com.solo4.calendarreminder.utils.calendar

import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import java.util.TimeZone

class AndroidCalendar : CalendarWrapper {

    private val timeZone: TimeZone = TimeZone.getDefault()
    private val locale: Locale = Locale.getDefault(Locale.Category.FORMAT)

    private val calendar: GregorianCalendar = GregorianCalendar(timeZone, locale)

    override val timeZoneOffset: Int = timeZone.rawOffset

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
            calendar.set(year, month - 1, day, 0, 0)

            calendar.timeInMillis
        }
    }

    private fun setTimeMillis(dateMillis: Long) {
        calendar.time = Date(dateMillis)
    }

    private fun invalidateCalendar() {
        calendar.clear()
    }
}