package com.solo4.core.calendar.model

import java.io.Serializable

actual class CalendarEvent(
    actual val eventId: Int = 0,
    actual val dayMillis: Long,
    actual val title: String,
    actual val description: String,
    actual val eventTimeMillis: Long,
) : Serializable