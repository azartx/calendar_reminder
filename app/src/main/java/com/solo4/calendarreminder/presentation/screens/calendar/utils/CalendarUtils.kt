package com.solo4.calendarreminder.presentation.screens.calendar.utils

import com.solo4.calendarreminder.utils.calendar.CalendarWrapper
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.util.Date
import java.util.Locale

val currentYear: Int
    get() = YearMonth.now().year
val currentMonth: Int
    get() = YearMonth.now().monthValue

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

fun Date.formatWithPattern(pattern: String = "dd.MM.yyyy 'in' HH:mm"): String {
    return SimpleDateFormat(pattern, Locale.getDefault())
        .format(this)
}

fun CalendarWrapper.formatDateIdToDayMillis(dayId: Long): Long {
    val dayIdString = dayId.toString()

    val year = dayIdString.substring(0, 3).toInt()
    val month = dayIdString.substring(4, 5).toInt()
    val day = dayIdString.substring(6, 7).toInt()

    return millisOf(year, month, day)
}