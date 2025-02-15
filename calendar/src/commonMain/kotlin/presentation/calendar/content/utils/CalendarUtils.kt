package com.solo4.calendarreminder.calendar.presentation.calendar.content.utils

import androidx.compose.runtime.Stable
import com.solo4.core.calendar.CalendarWrapper
import com.solo4.core.calendar.getDateFormatter

const val DATE_IN_TIME_PATTERN = "dd.MM.yyyy 'in' HH:mm"
const val DATE_PATTERN = "dd.MM.yyyy"

fun getFormattedDateId(
    day: Int,
    month: Int,
    year: Int
): Long {
    return "$year${toTwoDigitNumber(month)}${toTwoDigitNumber(day)}".toLong()
}

// Возвращает строку с числом. Если в входящем числе только одна цифра - к ней подставляется ноль спереди, если две -
// возвращается просто строка с двухзначным числом.
// Не предусмотрен приём числа с тремя и более символами
private fun toTwoDigitNumber(number: Int): String {
    return number.toString()
        .takeIf { it.length == 2 }
        ?: "0$number"
}

@Stable
fun Long.toDateByPattern(pattern: String = DATE_IN_TIME_PATTERN): String {
    return getDateFormatter().formatByPattern(this, pattern)
}

fun CalendarWrapper.formatDateIdToDayMillis(dayId: Long): Long {
    val dayIdString = dayId.toString()

    val year = dayIdString.substring(0, 4).toInt()
    val month = dayIdString.substring(4, 6).toInt()
    val day = dayIdString.substring(6, 8).toInt()

    return millisOf(year, month, day)
}

fun CalendarWrapper.addTimezoneOffset(millis: Long): Long {
    return millis + timeZoneOffset
}

fun CalendarWrapper.removeTimezoneOffset(millis: Long): Long {
    return millis - timeZoneOffset
}