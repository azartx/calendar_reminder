package com.solo4.core.calendar

actual fun getDateFormatter(): DateFormatter {
    //TODO("Not yet implemented")
    return object : DateFormatter {
        override fun formatByPattern(dateTimeMillis: Long, patterns: String): String {
            return "TODO"
        }
    }
}