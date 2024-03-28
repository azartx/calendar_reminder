package com.solo4.core.calendar

expect fun getDateFormatter(): DateFormatter

interface DateFormatter {

    fun formatByPattern(dateTimeMillis: Long, patterns: String): String
}