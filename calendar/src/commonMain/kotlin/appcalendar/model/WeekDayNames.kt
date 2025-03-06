package com.solo4.calendarreminder.calendar.appcalendar.model

import androidx.compose.runtime.Stable

@Stable
class WeekDayNames(
    monday: String,
    tuesday: String,
    wednesday: String,
    thursday: String,
    friday: String,
    saturday: String,
    sunday: String
) {

    val asList = listOf(monday, tuesday, wednesday, thursday, friday, saturday, sunday)

    override fun toString(): String {
        return asList.joinToString()
    }
}