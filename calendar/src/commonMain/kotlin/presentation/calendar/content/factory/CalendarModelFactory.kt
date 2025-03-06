package com.solo4.calendarreminder.calendar.presentation.calendar.content.factory

import appcalendar.model.AppCalendarItemModel
import appcalendar.model.AppCalendarModel
import appcalendar.model.AppCalendarRow
import com.solo4.core.calendar.CalendarWrapper
import com.solo4.core.calendar.YearMonth
import com.solo4.core.calendar.model.WeekDay

class CalendarModelFactory(
    private val calendar: CalendarWrapper
) {

    fun createMonthModels(year: Int, month: Int): AppCalendarModel {
        val yearMonth = YearMonth.create(year, month)
        return AppCalendarModel(
            modelFormattedDate = "${calendar.getDisplayMonthName(month)} $year",
            rows = getCalendarWeeks(yearMonth),
            dayNow = calendar.dayOfMonthOf(calendar.millisNow),
            yearNow = calendar.yearOf(calendar.millisNow),
            monthNow = calendar.monthOf(calendar.millisNow)
        )
    }

    private fun getCalendarWeeks(calendar: YearMonth): List<AppCalendarRow> {
        return getMonthCalendarDays(calendar).chunked(7).map { AppCalendarRow(it) }
    }

    private fun getMonthCalendarDays(calendar: YearMonth): List<AppCalendarItemModel> {

        val days = mutableListOf<AppCalendarItemModel>()

        val firstDayWeekNumber = calendar.dayOfWeekNumber(1)
        val lastDayWeekNumber = calendar.dayOfWeekNumber(calendar.days)

        val isFirstDayMonday = firstDayWeekNumber == WeekDay.Monday.number
        val isLastDaySunday = lastDayWeekNumber == WeekDay.Sunday.number

        if (!isFirstDayMonday) {

            val previousMonthYear = if (calendar.month == 1) calendar.year - 1 else calendar.year
            val previousMonthNumber = if (calendar.month == 1) 12 else calendar.month - 1
            val previousMonth = YearMonth.create(previousMonthYear, previousMonthNumber)

            val needDays = firstDayWeekNumber - 1

            for (day in (previousMonth.days - needDays)..< previousMonth.days) {
                days.add(
                    AppCalendarItemModel(
                        day = day,
                        month = previousMonthNumber,
                        year = previousMonthYear,
                        dayOfWeek = previousMonth.dayOfWeekNumber(day)
                    )
                )
            }
        }

        for (day in 1..calendar.days) {
            days.add(
                AppCalendarItemModel(
                    day = day,
                    month = calendar.month,
                    year = calendar.year,
                    dayOfWeek = calendar.dayOfWeekNumber(day)
                )
            )
        }

        if (!isLastDaySunday) {

            val needDays = 7 - lastDayWeekNumber

            val nextMonthYear = if (calendar.month == 12) calendar.year + 1 else calendar.year
            val nextMonthNumber = if (calendar.month == 12) 1 else calendar.month + 1
            val nextMonth = YearMonth.create(nextMonthYear, nextMonthNumber)

            for (day in 1..needDays) {
                days.add(
                    AppCalendarItemModel(
                        day = day,
                        month = nextMonthNumber,
                        year = nextMonthYear,
                        dayOfWeek = nextMonth.dayOfWeekNumber(day)
                    )
                )
            }
        }
        return days.toList()
    }
}