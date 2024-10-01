package com.solo4.core.calendar.model

expect class CalendarEvent {
    val eventId: Int // should be generated by room
    val dayMillis: Long
    val title: String
    val description: String
    val eventTimeMillis: Long
}