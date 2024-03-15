package com.solo4.calendarreminder.presentation.screens.calendar.factory

import com.solo4.calendarreminder.presentation.components.appcalendar.model.AppCalendarItemModel
import com.solo4.calendarreminder.presentation.components.appcalendar.model.AppCalendarModel
import com.solo4.calendarreminder.presentation.components.appcalendar.model.AppCalendarRow
import com.solo4.calendarreminder.presentation.screens.calendar.utils.WeekDay
import java.time.YearMonth

class CalendarModelFactory {

    fun createMonthModels(year: Int, month: Int): AppCalendarModel {
        val calendar = YearMonth.of(year, month)
        return AppCalendarModel(getCalendarWeeks(calendar))
    }

    private fun getCalendarWeeks(calendar: YearMonth): List<AppCalendarRow> {
        return getMonthCalendarDays(calendar).chunked(7).map { AppCalendarRow(it) }
    }

    private fun getMonthCalendarDays(calendar: YearMonth): List<AppCalendarItemModel> {

        val days = mutableListOf<AppCalendarItemModel>()

        val firstDayWeekNumber = calendar.atDay(1).dayOfWeek.value
        val lastDayWeekNumber = calendar.atDay(calendar.lengthOfMonth()).dayOfWeek.value

        val isFirstDayMonday = firstDayWeekNumber == WeekDay.Monday.number
        val isLastDaySunday = lastDayWeekNumber == WeekDay.Sunday.number

        if (!isFirstDayMonday) {

            val previousMonthYear = if (calendar.month.value == 1) calendar.year - 1 else calendar.year
            val previousMonthNumber = if (calendar.month.value == 1) 12 else calendar.month.value - 1
            val previousMonth = YearMonth.of(previousMonthYear, previousMonthNumber)

            val needDays = firstDayWeekNumber - 1

            for (day in (previousMonth.lengthOfMonth() - needDays)..< previousMonth.lengthOfMonth()) {
                days.add(
                    AppCalendarItemModel(
                        day = day,
                        month = previousMonthNumber,
                        year = previousMonthYear,
                        dayOfWeek = previousMonth.atDay(day).dayOfWeek.value
                    )
                )
            }
        }

        for (day in 1..calendar.lengthOfMonth()) {
            days.add(
                AppCalendarItemModel(
                    day = day,
                    month = calendar.monthValue,
                    year = calendar.year,
                    dayOfWeek = calendar.atDay(day).dayOfWeek.value
                )
            )
        }

        if (!isLastDaySunday) {

            val needDays = 7 - lastDayWeekNumber

            val nextMonthYear = if (calendar.month.value == 12) calendar.year + 1 else calendar.year
            val nextMonthNumber = if (calendar.month.value == 12) 1 else calendar.month.value + 1
            val nextMonth = YearMonth.of(nextMonthYear, nextMonthNumber)

            for (day in 1..needDays) {
                days.add(
                    AppCalendarItemModel(
                        day = day,
                        month = calendar.monthValue,
                        year = calendar.year,
                        dayOfWeek = nextMonth.atDay(day).dayOfWeek.value
                    )
                )
            }
        }
        return days.toList()
    }
}