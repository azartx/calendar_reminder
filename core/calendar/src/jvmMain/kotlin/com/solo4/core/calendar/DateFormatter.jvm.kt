package com.solo4.core.calendar

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

actual fun getDateFormatter(): DateFormatter {
    return object : DateFormatter {
        override fun formatByPattern(dateTimeMillis: Long, patterns: String): String {
            return SimpleDateFormat(patterns, Locale.getDefault()).format(Date(dateTimeMillis))
        }
    }
}