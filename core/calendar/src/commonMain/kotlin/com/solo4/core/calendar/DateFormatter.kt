package com.solo4.core.calendar

// TODO перейти на kotlin date
expect fun getDateFormatter(): DateFormatter

interface DateFormatter {

    fun formatByPattern(dateTimeMillis: Long, patterns: String): String
}