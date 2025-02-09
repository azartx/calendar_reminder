package com.solo4.calendarreminder.calendar.utils

import androidx.compose.material3.CalendarLocale
import java.util.Locale

actual fun getDefaultLocale(): CalendarLocale {
    return Locale.getDefault()
}