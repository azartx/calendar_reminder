package com.solo4.calendarreminder.presentation.components.appcalendar.model

data class AppCalendarModel(
    val rows: List<AppCalendarRow>,
    val formattedCurrentDate: String
)