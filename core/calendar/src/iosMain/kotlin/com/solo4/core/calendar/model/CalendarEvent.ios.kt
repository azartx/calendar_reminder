package com.solo4.core.calendar.model

actual class CalendarEvent(
    actual val eventId: Int,
    actual val dayMillis: Long,
    actual val title: String,
    actual val description: String,
    actual val eventTimeMillis: Long,
)