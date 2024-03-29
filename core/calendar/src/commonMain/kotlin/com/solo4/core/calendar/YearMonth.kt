package com.solo4.core.calendar

expect fun yearMonth(year: Int, month: Int): YearMonth

interface YearMonth {

    companion object {

        fun create(year: Int, month: Int): YearMonth {
            return yearMonth(year, month)
        }
    }


    val month: Int

    val year: Int

    val days: Int

    fun dayOfWeekNumber(day: Int): Int
}